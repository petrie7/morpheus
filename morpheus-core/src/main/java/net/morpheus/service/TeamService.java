package net.morpheus.service;

import net.morpheus.domain.Team;
import net.morpheus.persistence.mongo.MongoTeamRepository;

import java.util.List;

public class TeamService {

    private MongoTeamRepository teamRepository;

    public TeamService(MongoTeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void create(String teamName) {
        if (!teamRepository.findByName(teamName).isPresent()) {
            teamRepository.create(new Team(teamName));
        } else {
            throw new IllegalArgumentException(String.format("Team [%s] already exists", teamName));
        }
    }

    public List<Team> getAll() {
        return teamRepository.getAll();
    }
}
