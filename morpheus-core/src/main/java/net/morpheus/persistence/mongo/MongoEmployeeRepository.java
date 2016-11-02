package net.morpheus.persistence.mongo;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.persistence.EmployeeRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoEmployeeRepository implements EmployeeRepository {

    public static final String EMPLOYEE_COLLECTION = "employee";
    private MongoTemplate mongoTemplate;

    public MongoEmployeeRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void create(EmployeeDetails employee) {
        mongoTemplate.insert(employee, EMPLOYEE_COLLECTION);
    }

    @Override
    public void update(EmployeeDetails employeeDetails) {
        mongoTemplate.save(employeeDetails, EMPLOYEE_COLLECTION);
    }

    @Override
    public Optional<EmployeeDetails> findByName(String username) {
        return mongoTemplate
                .find(new Query(where("username").is(username)), EmployeeDetails.class, EMPLOYEE_COLLECTION)
                .stream()
                .findFirst();
    }

    @Override
    public void delete(EmployeeDetails employee) {
        mongoTemplate.findAllAndRemove(new Query(where("username").is(employee.username())), EmployeeDetails.class, EMPLOYEE_COLLECTION);
    }

    @Override
    public List<EmployeeDetails> getAll() {
        return mongoTemplate.findAll(EmployeeDetails.class, EMPLOYEE_COLLECTION);
    }

    @Override
    public List<EmployeeDetails> findByTeam(String teamName) {
        return mongoTemplate
                .find(new Query(where("team.name").is(teamName)), EmployeeDetails.class, EMPLOYEE_COLLECTION);
    }
}
