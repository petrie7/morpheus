package net.morpheus.persistence;

import net.morpheus.domain.Employee;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class EmployeeRepository {

    public static final String PEOPLE_COLLECTION = "employee";
    private MongoTemplate mongoTemplate;

    public EmployeeRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Employee findByName(String name) {
        return mongoTemplate.findOne(new Query(where("_id").is(name)), Employee.class, PEOPLE_COLLECTION);
    }

    public void create(Employee employee) {
        mongoTemplate.insert(employee, PEOPLE_COLLECTION);
    }

    public void delete(Employee employee) {
        mongoTemplate.remove(new Query(where("_id").is(employee.username())), PEOPLE_COLLECTION);
    }

    public void update(Employee employee) {
        mongoTemplate.save(employee, PEOPLE_COLLECTION);
    }
}
