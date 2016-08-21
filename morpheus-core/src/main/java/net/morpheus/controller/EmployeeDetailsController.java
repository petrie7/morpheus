package net.morpheus.controller;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.service.NewUserAuthenticator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeDetailsController {

    private final EmployeeRepository employeeRepository;
    private final NewUserAuthenticator newUserAuthenticator;

    public EmployeeDetailsController(EmployeeRepository employeeRepository, NewUserAuthenticator newUserAuthenticator) {
        this.employeeRepository = employeeRepository;
        this.newUserAuthenticator = newUserAuthenticator;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<EmployeeDetails> getAllEmployees() {
        return employeeRepository.getAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RolesAllowed("ROLE_MANAGER")
    public void create(@RequestBody EmployeeDetails employeeDetails) {
        newUserAuthenticator.validateUserCanBeCreated(employeeDetails.username());
        employeeRepository.create(
                employeeDetails
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    @RolesAllowed("ROLE_MANAGER")
    public void updateEmployeeDetails(@RequestBody EmployeeDetails employeeDetails){
        employeeRepository.update(
                employeeDetails
        );
    }
}
