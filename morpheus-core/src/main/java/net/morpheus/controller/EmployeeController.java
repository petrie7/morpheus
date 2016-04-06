package net.morpheus.controller;

import net.morpheus.domain.Employee;
import net.morpheus.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping("/employee")
    @ResponseBody
    public Employee read(Principal principal) {
        return employeeRepository.findByName(principal.getName());
    }

    @RequestMapping("/employee/all")
    @ResponseBody
    public List<Employee> readAll() {
        return employeeRepository.getAll();
    }
}
