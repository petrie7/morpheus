package net.morpheus.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.morpheus.domain.*;
import net.morpheus.domain.builder.EmployeeBuilder;
import net.morpheus.exception.UnauthorisedAccessException;
import net.morpheus.service.EmployeeDetailsService;
import net.morpheus.service.EmployeeRecordService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    private EmployeeDetailsService employeeDetailsService;
    private EmployeeRecordService employeeRecordService;

    public EmployeeController(EmployeeDetailsService employeeDetailsService, EmployeeRecordService employeeRecordService) {
        this.employeeDetailsService = employeeDetailsService;
        this.employeeRecordService = employeeRecordService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Employee getEmployeeForLoggedInUser(Principal principal) {
        List<EmployeeRecord> records = employeeRecordService.findWorkInProgressByName(principal.getName());
        return new Employee(employeeDetailsService.findByName(principal.getName()).get(),
                records.isEmpty() ? emptyRecord(principal.getName()) : records);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Employee getAllEmployeeRecordsForUser(@PathVariable String username, Principal loggedInUser) {
        EmployeeDetails employeeDetails = employeeDetailsService.findByName(username).get();
        if (isAuthorisedToView(loggedInUser, employeeDetails)) {
            List<EmployeeRecord> employeeRecords = employeeRecordService.findAllByName(username);
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
    public EmployeePrincipal authenticatedUser(UsernamePasswordAuthenticationToken principal) {
        EmployeeDetails employeeDetails = employeeDetailsService.findByName(principal.getName())
                .orElseGet(() -> EmployeeBuilder.anEmployee().withRole(Role.Manager).build());
        return new EmployeePrincipal(principal, employeeDetails.role());
    }

    private List<EmployeeRecord> emptyRecord(String username) {
        return singletonList(anEmployeeRecord()
                .withUsername(username)
                .build());
    }

    private boolean isAuthorisedToView(Principal principal, EmployeeDetails employeeDetails) {
        Optional<EmployeeDetails> loggedInUser = employeeDetailsService.findByName(principal.getName());
        if (loggedInUser.isPresent()) {
            return loggedInUser.get().role() == TeamLead && loggedInUser.get().team().equals(employeeDetails.team());
        } else {
            return true;
        }
    }

    private class EmployeePrincipal extends UsernamePasswordAuthenticationToken {

        @JsonProperty
        private final Role role;

        public EmployeePrincipal(UsernamePasswordAuthenticationToken principal, Role role) {
            super(principal.getPrincipal(), principal.getCredentials(), principal.getAuthorities());
            this.role = role;
        }

        public Role role(){
            return role;
        }


    }
}
