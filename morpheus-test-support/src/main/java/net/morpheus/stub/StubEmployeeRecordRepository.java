package net.morpheus.stub;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.persistence.EmployeeRecordRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StubEmployeeRecordRepository implements EmployeeRecordRepository {

    private List<EmployeeRecord> employeeRecords = new ArrayList<>();

    @Override
    public List<EmployeeRecord> findByName(String name) {
        return employeeRecords.stream()
                .filter(employeeRecord -> employeeRecord.username().equals(name))
                .sorted((o1, o2) -> o2.date().compareTo(o1.date()))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeRecord> getAll() {
        return employeeRecords;
    }

    @Override
    public void create(EmployeeRecord employeeRecord) {
        employeeRecord.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        employeeRecords.add(employeeRecord);
    }

    @Override
    public void delete(EmployeeRecord employeeRecord) {
        employeeRecords.remove(employeeRecord);
    }
}
