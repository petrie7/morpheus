package net.morpheus.config;

import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.service.EmployeeDetailsService;
import net.morpheus.service.NewUserAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ServiceConfig {

    @Bean
    @Autowired
    public EmployeeDetailsService employeeDetailsService(EmployeeRepository employeeRepository, NewUserAuthenticator newUserAuthenticator){
        return new EmployeeDetailsService(employeeRepository, newUserAuthenticator);
    }

}
