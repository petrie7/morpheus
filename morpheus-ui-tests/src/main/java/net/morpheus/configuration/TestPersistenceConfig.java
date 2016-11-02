package net.morpheus.configuration;


import net.morpheus.persistence.EmployeeRecordRepository;
import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.persistence.SkillTemplateRepository;
import net.morpheus.persistence.TeamRepository;
import net.morpheus.stub.StubEmployeeRecordRepository;
import net.morpheus.stub.StubEmployeeRepository;
import net.morpheus.stub.StubTeamRepository;
import net.morpheus.stub.StubTemplateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@SuppressWarnings("unused")
@Configuration
public class TestPersistenceConfig {

    @Bean
    public EmployeeRecordRepository employeeRecordRepository() throws UnknownHostException {
        return new StubEmployeeRecordRepository();
    }

    @Bean
    public EmployeeRepository employeeRepository() throws UnknownHostException {
        return new StubEmployeeRepository();
    }

    @Bean
    public SkillTemplateRepository skillTemplateRepository() throws UnknownHostException {
        return new StubTemplateRepository();
    }

    @Bean
    public TeamRepository teamRepository() throws UnknownHostException {
        return new StubTeamRepository();
    }
}
