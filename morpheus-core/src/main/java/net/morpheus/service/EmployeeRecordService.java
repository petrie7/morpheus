package net.morpheus.service;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.persistence.EmployeeRecordRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class EmployeeRecordService {

    private EmployeeRecordRepository employeeRecordRepository;

    public EmployeeRecordService(EmployeeRecordRepository employeeRecordRepository) {
        this.employeeRecordRepository = employeeRecordRepository;
    }

    public List<EmployeeRecord> findAllByName(String name) {
        return employeeRecordRepository.findByName(name);
    }

    public List<EmployeeRecord> findWorkInProgressByName(String name) {
        return findAllByName(name)
                .stream()
                .filter(employeeRecord -> !employeeRecord.isWorkInProgress())
                .collect(toList());
    }
}
