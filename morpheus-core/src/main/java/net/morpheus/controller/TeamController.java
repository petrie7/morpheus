package net.morpheus.controller;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Role;
import net.morpheus.domain.Team;
import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.persistence.TeamRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;

    public TeamController(TeamRepository teamRepository, EmployeeRepository employeeRepository) {
        this.teamRepository = teamRepository;
        this.employeeRepository = employeeRepository;
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
    public List<Team> retrieveAllTeams() {
        return teamRepository.getAll();
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public List<EmployeeDetails> teamMembers(Principal principal) {
        EmployeeDetails loggedInUser = employeeRepository.findByName(principal.getName()).get();
        if (loggedInUser.role() == Role.TeamLead) {
            return employeeRepository.findByTeam(loggedInUser.team().name())
                    .stream()
                    .filter(employeeDetails -> !employeeDetails.username().equals(loggedInUser.username()))
                    .collect(toList());
        } else {
            return emptyList();
        }
    }

}
