package net.morpheus.controller;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.persistence.EmployeeRecordRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeRecordController {

    private final EmployeeRecordRepository employeeRecordRepository;

    public EmployeeRecordController(EmployeeRecordRepository employeeRecordRepository) {
        this.employeeRecordRepository = employeeRecordRepository;
    }

    @RequestMapping(value = "employee/record", method = RequestMethod.POST)
    public void updateEmployeeRecord(@RequestBody EmployeeRecord employeeRecord) {
        employeeRecordRepository.create(
                employeeRecord
        );
    }

}
