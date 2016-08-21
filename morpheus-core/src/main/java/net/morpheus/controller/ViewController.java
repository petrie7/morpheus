package net.morpheus.controller;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.persistence.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ViewController {

    private final EmployeeRepository employeeRepository;

    public ViewController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping({"/", "/index"})
    public String greeting(Principal principal, Model model) {
        Optional<EmployeeDetails> employeeDetails = employeeRepository.findByName(principal.getName());
        if (employeeDetails.isPresent()) {
            model.addAttribute("role", employeeDetails.get().role().name());
        }
        return "index";
    }

}
