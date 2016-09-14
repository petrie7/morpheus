package net.morpheus.controller;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.service.EmployeeDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ViewController {

    private EmployeeDetailsService employeeDetailsService;

    public ViewController(EmployeeDetailsService employeeDetailsService) {
        this.employeeDetailsService = employeeDetailsService;
    }

    @RequestMapping({"/", "/index"})
    public String greeting(Principal principal, Model model) {
        Optional<EmployeeDetails> employeeDetails = employeeDetailsService.findByName(principal.getName());
        if (employeeDetails.isPresent()) {
            model.addAttribute("role", employeeDetails.get().role().name());
        }
        return "index";
    }

}
