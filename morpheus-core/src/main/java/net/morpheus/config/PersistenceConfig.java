package net.morpheus.config;

import com.mongodb.MongoClient;
import net.morpheus.persistence.EmployeeRecordRepository;
import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.persistence.SkillTemplateRepository;
import net.morpheus.persistence.TeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

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
    public EmployeeRecordRepository employeeRecordRepository() throws UnknownHostException {
        return new EmployeeRecordRepository(mongoTemplate());
    }

    @Bean
    public EmployeeRepository employeeRepository() throws UnknownHostException {
        return new EmployeeRepository(mongoTemplate());
    }

    @Bean
    public SkillTemplateRepository skillTemplateRepository() throws UnknownHostException {
        return new SkillTemplateRepository(mongoTemplate());
    }

    @Bean
    public TeamRepository teamRepository() throws UnknownHostException {
        return new TeamRepository(mongoTemplate());
    }


}
