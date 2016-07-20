package net.morpheus.config;

import com.mongodb.MongoClient;
import net.morpheus.domain.Employee;
import net.morpheus.domain.Level;
import net.morpheus.domain.Skill;
import net.morpheus.domain.Template;
import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.persistence.SkillTemplateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static net.morpheus.domain.builder.TemplateFieldBuilder.templateField;

@SuppressWarnings("unused")
@Configuration
public class PersistenceConfig {

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
    public static MongoDbFactory mongoDbFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(
                new MongoClient("localhost", 27017),
                "morpheus"
        );
    }

    @Bean
    public EmployeeRepository employeeRepository() throws UnknownHostException {
        EmployeeRepository employeeRepository = new EmployeeRepository(mongoTemplate());

        //Yikes! TODO: Remove this!
        employeeRepository.delete(Employee.developer("Laurence_Fishburne", null, Level.JuniorDeveloper));
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Functional Delivery", 5, "Shows Potential"));
        skills.add(new Skill("Quality Of Code", 5, "Fantastic"));
        skills.add(new Skill("Patterns", 5, "Shows Potential"));
        skills.add(new Skill("Refactoring Practice", 5, "Always looks to improve"));
        skills.add(new Skill("Refactoring Experience", 5, "Gaining"));
        skills.add(new Skill("Technical Debt", 5, ""));
        skills.add(new Skill("Estimating and Planning", 5, "Should contribute more"));
        skills.add(new Skill("Design", 5, "Research new patterns"));
        skills.add(new Skill("Solutions", 5, ""));
        skills.add(new Skill("TDD", 5, "Always looking to implement this"));
        skills.add(new Skill("Java", 5, "Continuously Improving"));
        skills.add(new Skill("Database Management Systems", 5, "Needs improving"));

        employeeRepository.create(Employee.developer("Laurence_Fishburne", skills, Level.JuniorDeveloper));
        //End Yikes!

        return employeeRepository;
    }

    @Bean
    public SkillTemplateRepository skillTemplateRepository() throws UnknownHostException {
        SkillTemplateRepository skillTemplateRepository = new SkillTemplateRepository(mongoTemplate());

        // Probably not wise TODO
        skillTemplateRepository.deleteAll();

        // More Yikes TODO
        skillTemplateRepository.saveOrUpdate(
                asList(new Template(
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
                                                .build()
                                )
                        ),
                        new Template(
                                "Soft Skills",
                                asList(
                                        templateField()
                                                .fieldName("Listening")
                                                .descriptionForJunior("People talking without speaking")
                                                .descriptionForMid("People hearing without listening")
                                                .descriptionForSenior("People writing songs that voices never share")
                                                .build(),
                                        templateField()
                                                .fieldName("Learning (How)")
                                                .descriptionForJunior("Horton knows a How")
                                                .descriptionForMid("Horton know a What")
                                                .descriptionForSenior("Horton knows a Who")
                                                .build(),
                                        templateField()
                                                .fieldName("Learning (What)")
                                                .descriptionForJunior("What is this?")
                                                .descriptionForMid("What is that?")
                                                .descriptionForSenior("What is life?")
                                                .build()
                                )
                        ),
                        new Template(
                                "Business Skills",
                                asList(
                                        templateField()
                                                .fieldName("Functionality")
                                                .descriptionForJunior("Likes Functionality")
                                                .descriptionForMid("Loves Functionality")
                                                .descriptionForSenior("Lives Functionality")
                                                .build(),
                                        templateField()
                                                .fieldName("Business Language / Terminology")
                                                .descriptionForJunior("Knows what stuff is")
                                                .descriptionForMid("Knows what stuff does")
                                                .descriptionForSenior("Knows the meaning of life, the universe and everything")
                                                .build()
                                )
                        )
                )
        );
        // Few

        return skillTemplateRepository;
    }


}
