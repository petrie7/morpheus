package net.morpheus.service;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Team;
import net.morpheus.persistence.mongo.MongoEmployeeRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class EmployeeDetailsServiceTest {

    private EmployeeDetailsService service;
    private MongoEmployeeRepository employeeRepository;

    @Before
    public void setup() {
        employeeRepository = mock(MongoEmployeeRepository.class);
        service = new EmployeeDetailsService(employeeRepository, mock(NewUserAuthenticator.class));
    }

    @Test
    public void getAllReturnsOnlyUnarchivedEmployees() throws Exception {
        EmployeeDetails unarchivedEmployee = new EmployeeDetails("abc", Level.JuniorDeveloper, Role.Developer, new Team("def"), false);
        EmployeeDetails archivedEmployee = new EmployeeDetails("aaa", Level.JuniorDeveloper, Role.Developer, new Team("bbb"), true);
        when(employeeRepository.getAll()).thenReturn(asList(
                unarchivedEmployee,
                archivedEmployee
        ));

        List<EmployeeDetails> allEmployees = service.getAll();

        assertThat(allEmployees, not(hasItem(archivedEmployee)));
    }

    @Test
    public void canUpdateTeamLeadersTeamToAnotherTeamWithoutATeamLead() {
        EmployeeDetails employeeDetails = new EmployeeDetails("Pedro", Level.SeniorDeveloper, Role.TeamLead, new Team("abc"), false);
        when(employeeRepository.findByTeam("abc")).thenReturn(Collections.emptyList());

        service.update(employeeDetails);

        verify(employeeRepository, times(1)).update(employeeDetails);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenTryingToMakeAnEmployeeATeamLeadOfATeamAlreadyWithOne() {
        EmployeeDetails employeeDetails = new EmployeeDetails("Pedro", Level.SeniorDeveloper, Role.TeamLead, new Team("abc"), false);
        when(employeeRepository.findByTeam("abc"))
                .thenReturn(asList(new EmployeeDetails("Bilbo", Level.SeniorDeveloper, Role.TeamLead, new Team("abc"), false)));

        service.update(employeeDetails);
    }

    @Test
    public void retrievesFellowNonArchivedTeamMembers() {
        String username = "Pedro";
        when(employeeRepository.findByName(username))
                .thenReturn(Optional.of(new EmployeeDetails(username, Level.SeniorDeveloper, Role.TeamLead, new Team("abc"), false)));

        EmployeeDetails unarchivedEmployee = new EmployeeDetails("Bilbo", Level.JuniorDeveloper, Role.Developer, new Team("abc"), false);
        EmployeeDetails archivedEmployee = new EmployeeDetails("Billy", Level.JuniorDeveloper, Role.Developer, new Team("abc"), true);

        when(employeeRepository.findByTeam("abc")).thenReturn(asList(unarchivedEmployee, archivedEmployee));

        List<EmployeeDetails> developersTeamMembers = service.getDevelopersTeamMembers(username);

        assertThat(developersTeamMembers.size(), is(1));
    }
}