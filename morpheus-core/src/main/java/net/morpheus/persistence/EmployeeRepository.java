package net.morpheus.persistence;

import net.morpheus.domain.EmployeeRecord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class EmployeeRepository {

    public static final String EMPLOYEE_COLLECTION = "employee";
    private MongoTemplate mongoTemplate;

    public EmployeeRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<EmployeeRecord> findByName(String name) {
        return mongoTemplate
                .find(new Query(where("username").is(name)), EmployeeRecord.class, EMPLOYEE_COLLECTION)
                .stream()
                .sorted((o1, o2) -> o2.date().compareTo(o1.date()))
                .collect(toList());
    }

    public List<EmployeeRecord> getAll() {
        return mongoTemplate.findAll(EmployeeRecord.class, EMPLOYEE_COLLECTION)
                .stream()
                .filter(employee -> employee.username() != null)
                .distinct()
                .collect(toList());
    }

    public void create(EmployeeRecord employeeRecord) {
        employeeRecord.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        mongoTemplate.insert(employeeRecord, EMPLOYEE_COLLECTION);
    }

    public void delete(EmployeeRecord employeeRecord) {
        mongoTemplate.findAllAndRemove(new Query(where("username").is(employeeRecord.username())), EmployeeRecord.class, EMPLOYEE_COLLECTION);
    }

}
