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
        skills.add(new Skill("Functional Skills", 1, "Always delivers on time"));
        skills.add(new Skill("Communication Skills", 3, ""));
        skills.add(new Skill("Haircut", 1, ""));
        skills.add(new Skill("Java", 8, ""));
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
