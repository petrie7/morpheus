package net.morpheus.controller;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.service.EmployeeDetailsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailsController {

    private EmployeeDetailsService employeeDetailsService;

    public EmployeeDetailsController(EmployeeDetailsService employeeDetailsService) {
        this.employeeDetailsService = employeeDetailsService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<EmployeeDetails> getAllEmployees() {
        return employeeDetailsService.getAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RolesAllowed("ROLE_MANAGER")
    public void create(@RequestBody EmployeeDetails employeeDetails) {
        employeeDetailsService.create(
                employeeDetails
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    @RolesAllowed("ROLE_MANAGER")
    public void updateEmployeeDetails(@RequestBody EmployeeDetails employeeDetails){
        employeeDetailsService.update(
                employeeDetails
        );
    }
}
