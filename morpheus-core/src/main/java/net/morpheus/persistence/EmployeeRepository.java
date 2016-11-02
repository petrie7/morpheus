package net.morpheus.persistence;

import net.morpheus.domain.EmployeeDetails;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    void create(EmployeeDetails employee);

    void update(EmployeeDetails employeeDetails);

    Optional<EmployeeDetails> findByName(String username);

    void delete(EmployeeDetails employee);

    List<EmployeeDetails> getAll();

    List<EmployeeDetails> findByTeam(String teamName);
}
