package net.morpheus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.morpheus.controller.EmployeeController;
import net.morpheus.controller.TemplateController;
import net.morpheus.domain.Employee;
import net.morpheus.domain.Level;
import net.morpheus.domain.Skill;
import net.morpheus.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MorpheusApplicationConfig {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public EmployeeRepository employeeRepository() {
        EmployeeRepository employeeRepository = new EmployeeRepository(mongoTemplate);

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
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory servletFactory = new TomcatEmbeddedServletContainerFactory();
        servletFactory.setPort(1999);
        return servletFactory;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Employee.class, new EmployeeDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    public EmployeeController employeeController() {
        return new EmployeeController();
    }

    @Bean
    public TemplateController templateController() {
        return new TemplateController();
    }

}
