package net.morpheus.controller;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Team;
import net.morpheus.service.EmployeeDetailsService;
import net.morpheus.service.TeamService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    private TeamService teamService;
    private EmployeeDetailsService employeeDetailsService;

    public TeamController(TeamService teamService, EmployeeDetailsService employeeDetailsService) {
        this.teamService = teamService;
        this.employeeDetailsService = employeeDetailsService;
    }

    @RequestMapping(value = "/{teamName}", method = RequestMethod.POST)
    @RolesAllowed(value = "ROLE_MANAGER")
    public void createNewTeam(@PathVariable String teamName) {
        teamService.create(teamName);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Team> retrieveAllTeams() {
        return teamService.getAll();
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public List<EmployeeDetails> teamMembers(Principal principal) {
        return employeeDetailsService.getDevelopersTeamMembers(principal.getName());
    }

}
