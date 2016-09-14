package net.morpheus.service;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Role;
import net.morpheus.persistence.EmployeeRepository;

import java.util.List;

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

    private boolean theTeamAlreadyHasATeamLead(EmployeeDetails employeeDetails) {
        List<EmployeeDetails> employeesInSelectedTeam = employeeRepository.findByTeam(employeeDetails.team().name());
        return employeesInSelectedTeam
                .stream()
                .filter(d -> d.role().equals(Role.TeamLead))
                .anyMatch(details -> details.team().equals(employeeDetails.team()));
    }

    public List<EmployeeDetails> getAll() {
        return employeeRepository.getAll();
    }
}
