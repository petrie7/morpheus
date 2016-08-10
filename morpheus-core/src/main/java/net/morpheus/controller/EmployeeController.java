package net.morpheus.controller;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.exception.NoUserExistsException;
import net.morpheus.persistence.EmployeeRecordRepository;
import net.morpheus.service.NewUserAuthenticator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class EmployeeController {

    @Resource
    private NewUserAuthenticator newUserAuthenticator;

    private final EmployeeRecordRepository employeeRecordRepository;

    public EmployeeController(EmployeeRecordRepository employeeRecordRepository) {
        this.employeeRecordRepository = employeeRecordRepository;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<EmployeeRecord> getEmployeeRecordsForLoggedInUser(Principal principal) {
        return employeeRecordRepository.findByName(principal.getName())
                .stream()
                .filter(employeeRecord -> !employeeRecord.isWorkInProgress())
                .collect(toList());
    }

    @RequestMapping(value = "/employee/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<EmployeeRecord> getAllEmployeeRecordsForUser(@PathVariable String username) {
        List<EmployeeRecord> employeeRecords = employeeRecordRepository.findByName(username);
        if (!employeeRecords.isEmpty()) {
            EmployeeRecord latestRecord = employeeRecords.get(0);
            List<EmployeeRecord> filteredList = employeeRecords
                    .stream().filter(employeeRecord -> !employeeRecord.isWorkInProgress()).collect(toList());
            filteredList.add(0, latestRecord);
            return filteredList;
        } else {
            throw new NoUserExistsException(username);
        }
    }

    @RequestMapping(value = "/employee/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<String> getAllUsernames() {
        return employeeRecordRepository.getAll().stream().map(EmployeeRecord::username).collect(toList());
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @RolesAllowed("ROLE_MANAGER")
    public void update(@RequestBody EmployeeRecord employeeRecord) {
        employeeRecordRepository.create(
                employeeRecord
        );
    }

    @RequestMapping(value = "/employee/create", method = RequestMethod.POST)
    public void create(@RequestBody EmployeeRecord employeeRecord) {
        newUserAuthenticator.validateUserCanBeCreated(employeeRecord.username());
        employeeRecordRepository.create(
                employeeRecord
        );
    }

    @RequestMapping(value = "/authenticated")
    public Principal authenticatedUser(Principal principal) {
        return principal;
    }
}
