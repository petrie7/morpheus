package net.morpheus.service;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Team;
import net.morpheus.persistence.EmployeeRepository;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeDetailsServiceTest {

    @Test
    public void getAllReturnsOnlyUnarchivedEmployees() throws Exception {
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeDetails unarchivedEmployee = new EmployeeDetails("abc", Level.JuniorDeveloper, Role.Developer, new Team("def"), false);
        EmployeeDetails archivedEmployee = new EmployeeDetails("aaa", Level.JuniorDeveloper, Role.Developer, new Team("bbb"), true);
        when(employeeRepository.getAll()).thenReturn(asList(
                unarchivedEmployee,
                archivedEmployee
        ));

        EmployeeDetailsService service = new EmployeeDetailsService(employeeRepository, mock(NewUserAuthenticator.class));

        List<EmployeeDetails> allEmployees = service.getAll();

        assertThat(allEmployees, not(hasItem(archivedEmployee)));
    }

}