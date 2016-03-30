package net.morpheus.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

@SuppressWarnings("unused")
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


}
