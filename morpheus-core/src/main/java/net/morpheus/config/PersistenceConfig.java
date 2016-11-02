package net.morpheus.config;

import com.mongodb.MongoClient;
import net.morpheus.persistence.TeamRepository;
import net.morpheus.persistence.mongo.MongoEmployeeRecordRepository;
import net.morpheus.persistence.mongo.MongoEmployeeRepository;
import net.morpheus.persistence.mongo.MongoSkillTemplateRepository;
import net.morpheus.persistence.mongo.MongoTeamRepository;
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
                new MongoClient("localhost", 27018),
                "morpheus"
        );
    }

    @Bean
    public MongoEmployeeRecordRepository employeeRecordRepository() throws UnknownHostException {
        return new MongoEmployeeRecordRepository(mongoTemplate());
    }

    @Bean
    public MongoEmployeeRepository employeeRepository() throws UnknownHostException {
        return new MongoEmployeeRepository(mongoTemplate());
    }

    @Bean
    public MongoSkillTemplateRepository skillTemplateRepository() throws UnknownHostException {
        return new MongoSkillTemplateRepository(mongoTemplate());
    }

    @Bean
    public TeamRepository teamRepository() throws UnknownHostException {
        return new MongoTeamRepository(mongoTemplate());
    }


}
