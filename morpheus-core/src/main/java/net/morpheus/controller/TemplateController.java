package net.morpheus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RestController()
public class TemplateController {

    @RequestMapping("template")
    public List<String> template() {
        return asList(
                "Functional Delivery",
                "Quality Of Code",
                "Patterns",
                "Refactoring Practice",
                "Refactoring Experience",
                "Technical Debt",
                "Estimating and Planning",
                "Design",
                "Solutions",
                "TDD",
                "Java",
                "Database Management Systems"
        );
    }
}
