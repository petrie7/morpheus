package net.morpheus.controller;

import net.morpheus.domain.*;
import net.morpheus.exception.UnauthorisedAccessException;
import net.morpheus.persistence.EmployeeRecordRepository;
import net.morpheus.persistence.EmployeeRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static net.morpheus.domain.Role.TeamLead;
import static net.morpheus.domain.builder.EmployeeRecordBuilder.anEmployeeRecord;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    private EmployeeRepository employeeRepository;
    private EmployeeRecordRepository employeeRecordRepository;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeRecordRepository employeeRecordRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeRecordRepository = employeeRecordRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Employee getEmployeeForLoggedInUser(Principal principal) {
        EmployeeDetails employeeDetails = employeeRepository.findByName(principal.getName()).get();
        List<EmployeeRecord> records = employeeRecordRepository.findByName(principal.getName())
                .stream()
                .filter(employeeRecord -> !employeeRecord.isWorkInProgress())
                .collect(toList());

        return new Employee(employeeDetails, records.isEmpty() ? emptyRecord(principal.getName()) : records);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Employee getAllEmployeeRecordsForUser(@PathVariable String username, Principal loggedInUser) {
        EmployeeDetails employeeDetails = employeeRepository.findByName(username).get();
        if (isAuthorisedToView(loggedInUser, employeeDetails)) {
            List<EmployeeRecord> employeeRecords = employeeRecordRepository.findByName(username);
            if (!employeeRecords.isEmpty()) {
                EmployeeRecord latestRecord = employeeRecords.get(0);
                List<EmployeeRecord> filteredList = employeeRecords
                        .stream().filter(employeeRecord -> !employeeRecord.isWorkInProgress()).collect(toList());
                if (latestRecord.isWorkInProgress()) {
                    filteredList.add(0, latestRecord);
                }
                return new Employee(employeeDetails, filteredList);
            }
            return new Employee(employeeDetails, emptyRecord(username));
        } else {
            throw new UnauthorisedAccessException();
        }
    }

    @RequestMapping(value = "/levels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Level> getAllLevels() {
        return asList(Level.values());
    }

    @RequestMapping(value = "/authenticated")
    public Principal authenticatedUser(Principal principal) {
        return principal;
    }

    private List<EmployeeRecord> emptyRecord(String username) {
        return singletonList(anEmployeeRecord()
                .withUsername(username)
                .build());
    }

    private boolean isAuthorisedToView(Principal principal, EmployeeDetails employeeDetails) {
        Optional<EmployeeDetails> loggedInUser = employeeRepository.findByName(principal.getName());
        if (loggedInUser.isPresent()) {
            return loggedInUser.get().role() == TeamLead && loggedInUser.get().team().equals(employeeDetails.team());
        } else {
            return true;
        }
    }
}
