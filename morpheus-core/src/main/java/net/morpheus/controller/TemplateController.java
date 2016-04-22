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
                "Functional Skills",
                "Communication Skills",
                "Haircut",
                "Java",
                "Pedro",
                "Speaking to Pedro",
                "Dancing with Pedro"
        );
    }
}
