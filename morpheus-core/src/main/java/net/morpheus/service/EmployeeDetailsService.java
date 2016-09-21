package net.morpheus.service;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Role;
import net.morpheus.persistence.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class EmployeeDetailsService {

    private EmployeeRepository employeeRepository;
    private NewUserAuthenticator newUserAuthenticator;

    public EmployeeDetailsService(EmployeeRepository employeeRepository, NewUserAuthenticator newUserAuthenticator) {
        this.employeeRepository = employeeRepository;
        this.newUserAuthenticator = newUserAuthenticator;
    }

    public void create(EmployeeDetails employeeDetails) {
        newUserAuthenticator.validateUserCanBeCreated(employeeDetails.username());
        employeeRepository.create(
                employeeDetails
        );
    }

    public void update(EmployeeDetails employeeDetails) {
        if (!theTeamAlreadyHasATeamLead(employeeDetails)) {
            employeeRepository.update(employeeDetails);
        } else {
            throw new IllegalArgumentException(String.format("Team [%s] already has a team lead", employeeDetails.team().name()));
        }
    }

    public Optional<EmployeeDetails> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<EmployeeDetails> getDevelopersTeamMembers(String username) {
        EmployeeDetails loggedInUser = employeeRepository.findByName(username).get();
        if (loggedInUser.role() == Role.TeamLead) {
            return employeeRepository.findByTeam(loggedInUser.team().name())
                    .stream()
                    .filter(employeeDetails -> !employeeDetails.username().equals(loggedInUser.username()))
                    .collect(toList());
        } else {
            return emptyList();
        }
    }

    public List<EmployeeDetails> getAll() {
        return employeeRepository.getAll().stream().filter(employeeDetails -> !employeeDetails.isArchived()).collect(toList());
    }

    private boolean theTeamAlreadyHasATeamLead(EmployeeDetails employeeDetails) {
        List<EmployeeDetails> employeesInSelectedTeam = employeeRepository.findByTeam(employeeDetails.team().name());
        return employeesInSelectedTeam
                .stream()
                .filter(d -> d.role().equals(Role.TeamLead))
                .anyMatch(details -> details.team().equals(employeeDetails.team()));
    }
}
