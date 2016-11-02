package net.morpheus.service;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.persistence.mongo.MongoEmployeeRecordRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class EmployeeRecordService {

    private MongoEmployeeRecordRepository employeeRecordRepository;

    public EmployeeRecordService(MongoEmployeeRecordRepository employeeRecordRepository) {
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
