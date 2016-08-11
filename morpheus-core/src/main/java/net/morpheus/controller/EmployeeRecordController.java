package net.morpheus.controller;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.persistence.EmployeeRecordRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static net.morpheus.domain.builder.EmployeeRecordBuilder.anEmployeeRecord;

@RestController
public class EmployeeRecordController {

    private final EmployeeRecordRepository employeeRecordRepository;

    public EmployeeRecordController(EmployeeRecordRepository employeeRecordRepository) {
        this.employeeRecordRepository = employeeRecordRepository;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<EmployeeRecord> getEmployeeRecordsForLoggedInUser(Principal principal) {
        List<EmployeeRecord> records = employeeRecordRepository.findByName(principal.getName())
                .stream()
                .filter(employeeRecord -> !employeeRecord.isWorkInProgress())
                .collect(toList());
        return records.isEmpty() ? emptyRecord(principal.getName()) : records;
    }

    @RequestMapping(value = "/employee/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RolesAllowed("ROLE_MANAGER")
    public List<EmployeeRecord> getAllEmployeeRecordsForUser(@PathVariable String username) {
        List<EmployeeRecord> employeeRecords = employeeRecordRepository.findByName(username);
        if (!employeeRecords.isEmpty()) {
            EmployeeRecord latestRecord = employeeRecords.get(0);
            List<EmployeeRecord> filteredList = employeeRecords
                    .stream().filter(employeeRecord -> !employeeRecord.isWorkInProgress()).collect(toList());
            if (latestRecord.isWorkInProgress()) {
                filteredList.add(0, latestRecord);
            }
            return filteredList;
        } else {
            return emptyRecord(username);
        }
    }

    private List<EmployeeRecord> emptyRecord(@PathVariable String username) {
        return singletonList(anEmployeeRecord()
                .withUsername(username)
                .withLastUpdatedDate(LocalDateTime.now().toString())
                .build());
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @RolesAllowed("ROLE_MANAGER")
    public void updateEmployeeRecord(@RequestBody EmployeeRecord employeeRecord) {
        employeeRecordRepository.create(
                employeeRecord
        );
    }
}
