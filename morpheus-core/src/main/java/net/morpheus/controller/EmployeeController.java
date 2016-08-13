package net.morpheus.controller;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.service.NewUserAuthenticator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
public class EmployeeController {

    @Resource
    private NewUserAuthenticator newUserAuthenticator;

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/employee/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<EmployeeDetails> getAllEmployees() {
        return employeeRepository.getAll();
    }

    @RequestMapping(value = "/employee/create", method = RequestMethod.POST)
    @RolesAllowed("ROLE_MANAGER")
    public void create(@RequestBody EmployeeDetails employeeDetails) {
        newUserAuthenticator.validateUserCanBeCreated(employeeDetails.username());
        employeeRepository.create(
                employeeDetails
        );
    }

    @RequestMapping(value = "/authenticated")
    public Principal authenticatedUser(Principal principal) {
        return principal;
    }
}
