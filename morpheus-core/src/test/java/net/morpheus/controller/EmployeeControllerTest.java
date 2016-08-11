package net.morpheus.controller;

import net.morpheus.domain.Team;
import net.morpheus.persistence.EmployeeRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static net.morpheus.domain.Team.Sonique;
import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    private EmployeeRepository repository;
    private EmployeeController employeeController;

    @Before
    public void setUp() {
        repository = Mockito.mock(EmployeeRepository.class);
        employeeController = new EmployeeController(repository);
    }

    @Test
    public void aManagerCanRetrieveAllUsernames() throws Exception {
        when(repository.getAll()).thenReturn(asList(
                anEmployee().withUsername("Pedr").build(),
                anEmployee().withUsername("Tymbo").build(),
                anEmployee().withUsername("Boris").build()
        ));
        List<String> allUsernames = employeeController.getAllUsernames();

        assertThat(allUsernames.size(), is(3));
    }

    @Ignore
    @Test
    public void returnsAListOfEmployeeNamesInATeam() {
        List<String> employeeNames = employeeController.getEmployeesForTeam(Sonique);

        when(repository.getAll()).thenReturn(asList(
                anEmployee().withUsername("Pedr").withTeam(Sonique).build(),
                anEmployee().withUsername("Tymbo").withTeam(Sonique).build(),
                anEmployee().withUsername("Boris").withTeam(Team.Lando).build())
        );

        assertThat(employeeNames.size(), is(2));

    }
}