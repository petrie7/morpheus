package net.morpheus.persistence;

import net.morpheus.domain.Employee;
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

    public List<Employee> findByName(String name) {
        return mongoTemplate
                .find(new Query(where("username").is(name)), Employee.class, EMPLOYEE_COLLECTION)
                .stream()
                .sorted((o1, o2) -> o2.date().compareTo(o1.date()))
                .collect(toList());
    }

    public List<Employee> getAll() {
        return mongoTemplate.findAll(Employee.class, EMPLOYEE_COLLECTION)
                .stream()
                .filter(employee -> employee.username() != null)
                .distinct()
                .collect(toList());
    }

    public void create(Employee employee) {
        employee.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        mongoTemplate.insert(employee, EMPLOYEE_COLLECTION);
    }

    public void delete(Employee employee) {
        mongoTemplate.findAllAndRemove(new Query(where("username").is(employee.username())), Employee.class, EMPLOYEE_COLLECTION);
    }

}
