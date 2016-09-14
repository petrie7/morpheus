package net.morpheus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.morpheus.controller.*;
import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.EmployeeRecord;
import net.morpheus.persistence.EmployeeRecordRepository;
import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.persistence.TeamRepository;
import net.morpheus.service.EmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MorpheusApplicationConfig {

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
        module.addDeserializer(EmployeeDetails.class, new EmployeeDeserializer());
        module.addDeserializer(EmployeeRecord.class, new EmployeeRecordDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    @Autowired
    public EmployeeRecordController employeeRecordController(EmployeeRecordRepository employeeRecordRepository) {
        return new EmployeeRecordController(employeeRecordRepository);
    }

    @Bean
    @Autowired
    public EmployeeController employeeController(EmployeeRepository employeeRepository, EmployeeRecordRepository employeeRecordRepository) {
        return new EmployeeController(employeeRepository, employeeRecordRepository);
    }

    @Bean
    @Autowired
    public EmployeeDetailsController employeeDetailsController(EmployeeDetailsService employeeDetailsService) {
        return new EmployeeDetailsController(employeeDetailsService);
    }

    @Bean
    @Autowired
    public TeamController teamController(TeamRepository teamRepository, EmployeeRepository employeeRepository) {
        return new TeamController(teamRepository, employeeRepository);
    }

    @Bean
    public TemplateController templateController() {
        return new TemplateController();
    }

    @Bean
    public GlobalControllerAdvice globalControllerAdvice() {
        return new GlobalControllerAdvice();
    }

}
