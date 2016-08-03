package net.morpheus.controller;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.exception.NoUserExistsException;
import net.morpheus.persistence.EmployeeRepository;
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

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<EmployeeRecord> getEmployeeRecordsForLoggedInUser(Principal principal) {
        return employeeRepository.findByName(principal.getName())
                .stream()
                .filter(employeeRecord -> !employeeRecord.isWorkInProgress())
                .collect(toList());
    }

    @RequestMapping(value = "/employee/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<EmployeeRecord> getAllEmployeeRecordsForUser(@PathVariable String username) {
        List<EmployeeRecord> employeeRecords = employeeRepository.findByName(username);
        if (!employeeRecords.isEmpty()) {
            EmployeeRecord latestRecord = employeeRecords.get(employeeRecords.size() - 1);
            List<EmployeeRecord> filteredList = employeeRecords
                    .stream().filter(employeeRecord -> !employeeRecord.isWorkInProgress()).collect(toList());
            filteredList.add(latestRecord);
            return filteredList;
        } else {
            throw new NoUserExistsException(username);
        }
    }

    @RequestMapping(value = "/employee/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<String> getAllUsernames() {
        return employeeRepository.getAll().stream().map(EmployeeRecord::username).collect(toList());
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @RolesAllowed("ROLE_MANAGER")
    public void update(@RequestBody EmployeeRecord employeeRecord) {
        employeeRepository.create(
                employeeRecord
        );
    }

    @RequestMapping(value = "/employee/create", method = RequestMethod.POST)
    public void create(@RequestBody EmployeeRecord employeeRecord) {
        newUserAuthenticator.validateUserCanBeCreated(employeeRecord.username());
        employeeRepository.create(
                employeeRecord
        );
    }

    @RequestMapping(value = "/authenticated")
    public Principal authenticatedUser(Principal principal) {
        return principal;
    }
}
