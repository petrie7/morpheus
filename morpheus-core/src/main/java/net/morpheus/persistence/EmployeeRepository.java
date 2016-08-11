package net.morpheus.persistence;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.exception.NoUserExistsException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class EmployeeRepository {

    public static final String EMPLOYEE_COLLECTION = "employee";
    private MongoTemplate mongoTemplate;

    public EmployeeRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void create(EmployeeDetails employee) {
        mongoTemplate.insert(employee, EMPLOYEE_COLLECTION);
    }

    public EmployeeDetails findByName(String username) {
        Optional<EmployeeDetails> employee = mongoTemplate
                .find(new Query(where("username").is(username)), EmployeeDetails.class, EMPLOYEE_COLLECTION)
                .stream()
                .findFirst();
        if (employee.isPresent()) {
            return employee.get();
        } else throw new NoUserExistsException(username);

    }

    public void delete(EmployeeDetails employee) {
        mongoTemplate.findAllAndRemove(new Query(where("username").is(employee.username())), EmployeeDetails.class, EMPLOYEE_COLLECTION);
    }

    public List<EmployeeDetails> getAll() {
        return mongoTemplate.findAll(EmployeeDetails.class, EMPLOYEE_COLLECTION);
    }
}
