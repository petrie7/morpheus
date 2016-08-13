package net.morpheus.persistence;

import net.morpheus.domain.Team;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class TeamRepository {

    public static final String TEAM_COLLECTION = "team";
    private MongoTemplate mongoTemplate;

    public TeamRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void create(Team team) {
        mongoTemplate.insert(team, TEAM_COLLECTION);
    }

    public Optional<Team> findByName(String teamName) {
        return mongoTemplate
                .find(new Query(where("name").is(teamName)), Team.class, TEAM_COLLECTION)
                .stream()
                .findFirst();
    }

    public List<Team> getAll() {
        return mongoTemplate.findAll(Team.class, TEAM_COLLECTION);
    }
}
