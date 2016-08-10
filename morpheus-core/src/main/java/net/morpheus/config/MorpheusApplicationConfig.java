package net.morpheus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.morpheus.controller.EmployeeController;
import net.morpheus.controller.GlobalControllerAdvice;
import net.morpheus.controller.TemplateController;
import net.morpheus.domain.EmployeeRecord;
import net.morpheus.persistence.EmployeeRecordRepository;
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
        module.addDeserializer(EmployeeRecord.class, new EmployeeDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    @Autowired
    public EmployeeController employeeController(EmployeeRecordRepository employeeRecordRepository) {
        return new EmployeeController(employeeRecordRepository);
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
