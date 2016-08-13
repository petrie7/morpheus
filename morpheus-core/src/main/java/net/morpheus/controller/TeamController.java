package net.morpheus.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.morpheus.domain.Team;
import net.morpheus.persistence.TeamRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/team")
public class TeamController {

    private TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @RequestMapping(value = "/{teamName}", method = RequestMethod.POST)
    @RolesAllowed(value = "ROLE_MANAGER")
    public void createNewTeam(@PathVariable String teamName) {
        if (!teamRepository.findByName(teamName).isPresent()) {
            teamRepository.create(new Team(teamName));
        } else {
            throw new IllegalArgumentException(String.format("Team [%s] already exists", teamName));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @RolesAllowed(value = "ROLE_MANAGER")
    public List<Team> retrieveAllTeams() {
        return teamRepository.getAll();
    }

}
