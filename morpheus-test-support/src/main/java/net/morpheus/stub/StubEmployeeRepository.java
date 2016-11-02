package net.morpheus.stub;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.persistence.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StubEmployeeRepository implements EmployeeRepository {
    private List<EmployeeDetails> employees = new ArrayList<>();

    @Override
    public void create(EmployeeDetails employee) {
        employees.add(employee);
    }

    @Override
    public void update(EmployeeDetails employeeDetails) {
        employees.removeIf(details -> details.username().equals(employeeDetails.username()));
        employees.add(employeeDetails);
    }

    @Override
    public Optional<EmployeeDetails> findByName(String username) {
        return employees.stream()
                .filter(employeeDetails -> employeeDetails.username().equals(username))
                .findFirst();
    }

    @Override
    public void delete(EmployeeDetails employee) {
        update(employee);
    }

    @Override
    public List<EmployeeDetails> getAll() {
        return employees;
    }

    @Override
    public List<EmployeeDetails> findByTeam(String teamName) {
        return employees.stream()
                .filter(employeeDetails -> employeeDetails.team() != null)
                .filter(employeeDetails -> employeeDetails.team().name().equals(teamName))
                .collect(Collectors.toList());
    }
}
