package net.morpheus.controller;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.persistence.EmployeeRecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.Principal;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.Arrays.asList;
import static net.morpheus.domain.builder.EmployeeRecordBuilder.anEmployeeRecord;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    private EmployeeController employeeController;
    private EmployeeRecordRepository repository;

    @Before
    public void setUp() {
        repository = Mockito.mock(EmployeeRecordRepository.class);
        employeeController = new EmployeeController(repository);
    }

    @Test
    public void loggedInUserShouldNotSeeWorkInProgressRecords() throws Exception {
        when(repository.findByName("Pedr")).thenReturn(asList(
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
        EmployeeRecord expectedLatestWorkInProgress = anEmployeeRecord().withUsername("Pedr")
                .withLastUpdatedDate(now().format(ISO_DATE_TIME))
                .isWorkInProgress(true)
                .build();

        EmployeeRecord expectedLatestCompleteSave = anEmployeeRecord().withUsername("Pedr")
                .withLastUpdatedDate(now().minusMinutes(5L).format(ISO_DATE_TIME))
                .isWorkInProgress(false)
                .build();

        EmployeeRecord oldWorkInProgress = anEmployeeRecord().withUsername("Pedr")
                .withLastUpdatedDate(now().minusMinutes(10L).format(ISO_DATE_TIME))
                .isWorkInProgress(true)
                .build();

        when(repository.findByName("Pedr")).thenReturn(asList(
                expectedLatestWorkInProgress,
                expectedLatestCompleteSave,
                oldWorkInProgress
        ));
        List<EmployeeRecord> actualRecords = employeeController.getAllEmployeeRecordsForUser("Pedr");

        assertThat(actualRecords.size(), is(2));
        assertThat(actualRecords.get(0), is(expectedLatestWorkInProgress));
        assertThat(actualRecords.get(1), is(expectedLatestCompleteSave));
    }

    @Test
    public void aManagerCanRetrieveAllUsernames() throws Exception {
        when(repository.getAll()).thenReturn(asList(
                anEmployeeRecord().withUsername("Pedr").isWorkInProgress(true).build(),
                anEmployeeRecord().withUsername("Tymbo").isWorkInProgress(true).build(),
                anEmployeeRecord().withUsername("Boris").isWorkInProgress(true).build()
        ));
        List<String> allUsernames = employeeController.getAllUsernames();

        assertThat(allUsernames.size(), is(3));
    }

    @Test
    public void returnsAListOfCompleteSavesForAUser() {
        String username = "Pedr";
        when(repository.findByName(username)).thenReturn(asList(
                anEmployeeRecord().withUsername(username).build(),
                anEmployeeRecord().withUsername(username).build(),
                anEmployeeRecord().withUsername(username).build()
        ));

        List<EmployeeRecord> allEmployeeRecordsForUser = employeeController.getAllEmployeeRecordsForUser(username);

        assertThat(allEmployeeRecordsForUser.size(), is(3));
    }


}