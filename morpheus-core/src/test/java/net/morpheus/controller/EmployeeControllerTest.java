package net.morpheus.controller;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.persistence.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static net.morpheus.domain.builder.EmployeeRecordBuilder.anEmployeeRecord;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    private EmployeeController employeeController;
    private EmployeeRepository repository;

    @Before
    public void setUp() {
        repository = Mockito.mock(EmployeeRepository.class);
        employeeController = new EmployeeController(repository);
    }

    @Test
    public void loggedInUserShouldNotSeeWorkInProgressRecords() throws Exception {
        when(repository.findByName("Pedr")).thenReturn(Arrays.asList(
                anEmployeeRecord().withUsername("Pedr").isWorkInProgress(true).build(),
                anEmployeeRecord().withUsername("Pedr").isWorkInProgress(false).build()));
        Principal principle = Mockito.mock(Principal.class);
        when(principle.getName()).thenReturn("Pedr");
        List<EmployeeRecord> employeeRecordsForLoggedInUser = employeeController.getEmployeeRecordsForLoggedInUser(principle);

        assertThat(employeeRecordsForLoggedInUser.size(), is(1));
        assertFalse(employeeRecordsForLoggedInUser.get(0).isWorkInProgress());
    }

    @Test
    public void aManagerCanSeeAllEmployeeRecordsAndMostRecentWorkInProgress() throws Exception {
        when(repository.findByName("Pedr")).thenReturn(Arrays.asList(
                anEmployeeRecord().withUsername("Pedr").isWorkInProgress(true).build(),
                anEmployeeRecord().withUsername("Pedr").isWorkInProgress(false).build(),
                anEmployeeRecord().withUsername("Pedr").isWorkInProgress(true).build()
        ));
        List<EmployeeRecord> actualRecords = employeeController.getAllEmployeeRecordsForUser("Pedr");

        assertThat(actualRecords.size(), is(2));
        assertFalse(actualRecords.get(0).isWorkInProgress());
        assertTrue(actualRecords.get(1).isWorkInProgress());
    }

    @Test
    public void aManagerCanRetrieveAllUsernames() throws Exception {
        when(repository.getAll()).thenReturn(Arrays.asList(
                anEmployeeRecord().withUsername("Pedr").isWorkInProgress(true).build(),
                anEmployeeRecord().withUsername("Tymbo").isWorkInProgress(true).build(),
                anEmployeeRecord().withUsername("Boris").isWorkInProgress(true).build()
        ));
        List<String> allUsernames = employeeController.getAllUsernames();

        assertThat(allUsernames.size(), is(3));
    }


}