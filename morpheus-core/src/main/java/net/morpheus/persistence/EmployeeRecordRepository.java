package net.morpheus.persistence;

import net.morpheus.domain.EmployeeRecord;

import java.util.List;

public interface EmployeeRecordRepository {
    List<EmployeeRecord> findByName(String name);

    List<EmployeeRecord> getAll();

    void create(EmployeeRecord employeeRecord);

    void delete(EmployeeRecord employeeRecord);
}
