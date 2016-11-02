package net.morpheus.config;

import net.morpheus.persistence.mongo.MongoEmployeeRecordRepository;
import net.morpheus.persistence.mongo.MongoEmployeeRepository;
import net.morpheus.persistence.mongo.MongoTeamRepository;
import net.morpheus.service.EmployeeDetailsService;
import net.morpheus.service.EmployeeRecordService;
import net.morpheus.service.NewUserAuthenticator;
import net.morpheus.service.TeamService;
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
    public EmployeeDetailsService employeeDetailsService(MongoEmployeeRepository employeeRepository, NewUserAuthenticator newUserAuthenticator) {
        return new EmployeeDetailsService(employeeRepository, newUserAuthenticator);
    }

    @Bean
    @Autowired
    public EmployeeRecordService employeeRecordService(MongoEmployeeRecordRepository employeeRecordRepository) {
        return new EmployeeRecordService(employeeRecordRepository);
    }

    @Bean
    @Autowired
    public TeamService teamService(MongoTeamRepository teamRepository) {
        return new TeamService(teamRepository);
    }

}
