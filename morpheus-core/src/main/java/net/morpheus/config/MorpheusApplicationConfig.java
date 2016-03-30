package net.morpheus.config;

import net.morpheus.controller.EmployeeController;
import net.morpheus.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MorpheusApplicationConfig {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public EmployeeRepository employeeRepository() {
        EmployeeRepository employeeRepository = new EmployeeRepository(mongoTemplate);
//        HashMap<String, Integer> skills = new HashMap<>();
//        skills.put("Pedr", 1);
//        employeeRepository.create(new Employee("a", Role.Developer, skills));
        return employeeRepository;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory servletFactory = new TomcatEmbeddedServletContainerFactory();
        servletFactory.setPort(1999);
        return servletFactory;
    }

    @Bean
    public EmployeeController employeeController() {
        return new EmployeeController();
    }

}
