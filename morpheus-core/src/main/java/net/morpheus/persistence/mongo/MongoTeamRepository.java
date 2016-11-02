package net.morpheus.persistence.mongo;

import net.morpheus.domain.Team;
import net.morpheus.persistence.TeamRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoTeamRepository implements TeamRepository {

    public static final String TEAM_COLLECTION = "team";
    private MongoTemplate mongoTemplate;

    public MongoTeamRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void create(Team team) {
        mongoTemplate.insert(team, TEAM_COLLECTION);
    }

    @Override
    public Optional<Team> findByName(String teamName) {
        return mongoTemplate
                .find(new Query(where("name").is(teamName)), Team.class, TEAM_COLLECTION)
                .stream()
                .findFirst();
    }

    @Override
    public List<Team> getAll() {
        return mongoTemplate.findAll(Team.class, TEAM_COLLECTION);
    }
}
