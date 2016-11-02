package net.morpheus.persistence;

import net.morpheus.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    void create(Team team);

    Optional<Team> findByName(String teamName);

    List<Team> getAll();
}
