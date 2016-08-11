package net.morpheus.persistence;

import net.morpheus.domain.EmployeeRecord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class EmployeeRecordRepository {

    public static final String EMPLOYEE_RECORD_COLLECTION = "employee_record";
    private MongoTemplate mongoTemplate;

    public EmployeeRecordRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<EmployeeRecord> findByName(String name) {
        return mongoTemplate
                .find(new Query(where("username").is(name)), EmployeeRecord.class, EMPLOYEE_RECORD_COLLECTION)
                .stream()
                .sorted((o1, o2) -> o2.date().compareTo(o1.date()))
                .collect(toList());
    }

    public List<EmployeeRecord> getAll() {
        List<EmployeeRecord> distinctList = new ArrayList<>();
        List<EmployeeRecord> uniques = new ArrayList<>();

        for (EmployeeRecord employeeRecord : mongoTemplate.findAll(EmployeeRecord.class, EMPLOYEE_RECORD_COLLECTION)) {
            if (distinctList.size() == 0) {
                distinctList.add(employeeRecord);
            }
            uniques.addAll(distinctList
                    .stream()
                    .filter(record -> !record.username().equals(employeeRecord.username()))
                    .map(record -> employeeRecord)
                    .collect(Collectors.toList()));

        }
        distinctList.addAll(uniques);
        return distinctList;
    }

    public void create(EmployeeRecord employeeRecord) {
        employeeRecord.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        mongoTemplate.insert(employeeRecord, EMPLOYEE_RECORD_COLLECTION);
    }

    public void delete(EmployeeRecord employeeRecord) {
        mongoTemplate.findAllAndRemove(new Query(where("username").is(employeeRecord.username())), EmployeeRecord.class, EMPLOYEE_RECORD_COLLECTION);
    }

}
