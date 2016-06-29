package net.morpheus.controller;

import net.morpheus.domain.Employee;
import net.morpheus.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Employee> read(Principal principal) {
        return employeeRepository.findByName(principal.getName());
    }

    @RequestMapping(value = "/employee/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<Employee> getEmployee(@PathVariable String username) {
        return employeeRepository.findByName(username);
    }

    @RequestMapping(value = "/employee/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<String> readAll() {
        return employeeRepository.getAll().stream().map(Employee::username).collect(Collectors.toList());
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @RolesAllowed("ROLE_MANAGER")
    public void update(@RequestBody Employee employee) {
        employeeRepository.create(
                employee
        );
    }

    @RequestMapping(value = "/employee/create", method = RequestMethod.POST)
    public void create(@RequestBody Employee employee) {
        employeeRepository.create(
                employee
        );
    }
}
