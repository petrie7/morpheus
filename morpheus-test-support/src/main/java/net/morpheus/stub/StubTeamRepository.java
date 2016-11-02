package net.morpheus.stub;

import net.morpheus.domain.Team;
import net.morpheus.persistence.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StubTeamRepository implements TeamRepository {

    private List<Team> teams = new ArrayList<>();

    @Override
    public void create(Team team) {
        teams.add(team);
    }

    @Override
    public Optional<Team> findByName(String teamName) {
        return teams.stream().filter(team -> team.name().equals(teamName)).findFirst();
    }

    @Override
    public List<Team> getAll() {
        return teams;
    }
}
