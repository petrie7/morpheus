package net.morpheus.controller;

import net.morpheus.domain.Employee;
import net.morpheus.domain.Role;
import net.morpheus.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Employee read(Principal principal) {
        return employeeRepository.findByName(principal.getName());
    }

    @RequestMapping(value = "/employee/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Employee> readAll() {
        return employeeRepository.getAll();
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Map<String, Object> employeeMap) {
        employeeRepository.update(
                new Employee(
                        employeeMap.get("username").toString(),
                        Role.valueOf(employeeMap.get("role").toString()),
                        (Map<String, Integer>) employeeMap.get("skills")
                )
        );
    }
}
