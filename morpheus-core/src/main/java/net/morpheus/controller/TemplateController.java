package net.morpheus.controller;

import net.morpheus.domain.Template;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;
import static net.morpheus.domain.builder.TemplateFieldBuilder.templateField;

@RestController
public class TemplateController {

    @RequestMapping("template")
    public List<Template> template() {
        return asList(
                new Template(
                        "Technical Skills",
                        asList(
                                templateField()
                                        .fieldName("Functional Delivery")
                                        .descriptionForJunior("Should know what Pipeline is")
                                        .descriptionForMid("Should have deployed using Pipeline")
                                        .descriptionForSenior("Should be at one with Pipeline")
                                        .build(),
                                templateField()
                                        .fieldName("Quality Of Code")
                                        .descriptionForJunior("Should be able to write some code")
                                        .descriptionForMid("Should know what an If Condition is")
                                        .descriptionForSenior("Should know who Uncle Bob is")
                                        .build(),
                                templateField()
                                        .fieldName("Patterns")
                                        .descriptionForJunior("Knows the difference between a circle and a square")
                                        .descriptionForMid("Knowing that JSON isn't a person")
                                        .descriptionForSenior("Has read GOF Design Patterns cover to cover")
                                        .build(),
                                templateField()
                                        .fieldName("Refactoring Practice")
                                        .descriptionForJunior("Does not use BlueJ")
                                        .descriptionForMid("Uses TABS over SPACES")
                                        .descriptionForSenior("Has read Martin Fowler's Refactoring")
                                        .build(),
                                templateField()
                                        .fieldName("Refactoring Experience")
                                        .descriptionForJunior("Uses IntelliJ")
                                        .descriptionForMid("Can extract a variable")
                                        .descriptionForSenior("Is Martin Fowler")
                                        .build(),
                                templateField()
                                        .fieldName("Technical Debt")
                                        .descriptionForJunior("Doesn't introduce technical debt")
                                        .descriptionForMid("Reduces technical debt")
                                        .descriptionForSenior("Never introduces technical debt")
                                        .build(),
                                templateField()
                                        .fieldName("Solutions")
                                        .descriptionForJunior("Uses the word 'key' when discussing a solution")
                                        .descriptionForMid("Declines the solution")
                                        .descriptionForSenior("Is the solution")
                                        .build(),
                                templateField()
                                        .fieldName("TDD")
                                        .descriptionForJunior("Writes a test with no assertions")
                                        .descriptionForMid("Writes acceptance tests")
                                        .descriptionForSenior("Doesn't need tests")
                                        .build(),
                                templateField()
                                        .fieldName("Java")
                                        .descriptionForJunior("Can do the Dog > Animal inheritance example")
                                        .descriptionForMid("Can add different dogs into the mixer")
                                        .descriptionForSenior("Is Joshua Bloch")
                                        .build(),
                                templateField()
                                        .fieldName("Database Management Systems")
                                        .descriptionForJunior("Stores stuff in an Array List and calls it persistence")
                                        .descriptionForMid("Loves Oracle")
                                        .descriptionForSenior("Hates Oracle")
                                        .build()
                        )
                ),
                new Template(
                        "Soft Skills",
                        asList(
                                templateField()
                                        .fieldName("Estimating and Planning")
                                        .descriptionForJunior("Knows the difference in T-Shirt sizes")
                                        .descriptionForMid("Can draw circles, squares and lines in between")
                                        .descriptionForSenior("Can estimate and plan without knowing what it is they are estimating")
                                        .build(),
                                templateField()
                                        .fieldName("Design")
                                        .descriptionForJunior("Knows a design isn't the squares in BlueJ")
                                        .descriptionForMid("Has considered Clean Architecture by Uncle Bob")
                                        .descriptionForSenior("Has tried and discarded Clean Architecture by Uncle Bob")
                                        .build()
                        )
                )
        );
    }
}
