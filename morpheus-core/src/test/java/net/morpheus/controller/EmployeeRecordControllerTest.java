package net.morpheus.controller;

import net.morpheus.persistence.EmployeeRecordRepository;
import org.junit.Before;
import org.mockito.Mockito;

public class EmployeeRecordControllerTest {

    private EmployeeRecordRepository repository;
    private EmployeeRecordController employeeRecordController;

    @Before
    public void setUp() {
        repository = Mockito.mock(EmployeeRecordRepository.class);
        employeeRecordController = new EmployeeRecordController(repository);
    }

//    @Test
//    public void loggedInUserShouldNotSeeWorkInProgressRecords() throws Exception {
//        when(repository.findByName("Pedr")).thenReturn(asList(
//                anEmployeeRecord().withUsername("Pedr").isWorkInProgress(true).build(),
//                anEmployeeRecord().withUsername("Pedr").isWorkInProgress(false).build()));
//        Principal principle = Mockito.mock(Principal.class);
//        when(principle.getName()).thenReturn("Pedr");
//        List<EmployeeRecord> employeeRecordsForLoggedInUser = employeeRecordController.getEmployeeRecordsForLoggedInUser(principle);
//
//        assertThat(employeeRecordsForLoggedInUser.size(), is(1));
//        assertFalse(employeeRecordsForLoggedInUser.get(0).isWorkInProgress());
//    }
//
//    @Test
//    public void aManagerCanSeeAllEmployeeRecordsAndMostRecentWorkInProgress() throws Exception {
//        EmployeeRecord expectedLatestWorkInProgress = anEmployeeRecord().withUsername("Pedr")
//                .withLastUpdatedDate(now().format(ISO_DATE_TIME))
//                .isWorkInProgress(true)
//                .build();
//
//        EmployeeRecord expectedLatestCompleteSave = anEmployeeRecord().withUsername("Pedr")
//                .withLastUpdatedDate(now().minusMinutes(5L).format(ISO_DATE_TIME))
//                .isWorkInProgress(false)
//                .build();
//
//        EmployeeRecord oldWorkInProgress = anEmployeeRecord().withUsername("Pedr")
//                .withLastUpdatedDate(now().minusMinutes(10L).format(ISO_DATE_TIME))
//                .isWorkInProgress(true)
//                .build();
//
//        when(repository.findByName("Pedr")).thenReturn(asList(
//                expectedLatestWorkInProgress,
//                expectedLatestCompleteSave,
//                oldWorkInProgress
//        ));
//        List<EmployeeRecord> actualRecords = employeeRecordController.getAllEmployeeRecordsForUser("Pedr");
//
//        assertThat(actualRecords.size(), is(2));
//        assertThat(actualRecords.get(0), is(expectedLatestWorkInProgress));
//        assertThat(actualRecords.get(1), is(expectedLatestCompleteSave));
//    }
//
//    @Test
//    public void returnsAListOfCompleteSavesForAUser() {
//        String username = "Pedr";
//        when(repository.findByName(username)).thenReturn(asList(
//                anEmployeeRecord().withUsername(username).build(),
//                anEmployeeRecord().withUsername(username).build(),
//                anEmployeeRecord().withUsername(username).build()
//        ));
//
//        List<EmployeeRecord> allEmployeeRecordsForUser = employeeRecordController.getAllEmployeeRecordsForUser(username);
//
//        assertThat(allEmployeeRecordsForUser.size(), is(3));
//    }
}