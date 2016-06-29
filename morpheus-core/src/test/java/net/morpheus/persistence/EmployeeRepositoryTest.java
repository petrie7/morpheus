package net.morpheus.persistence;

import net.morpheus.domain.Employee;
import net.morpheus.domain.Level;
import net.morpheus.domain.Skill;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static net.morpheus.domain.Role.Developer;
import static net.morpheus.domain.Role.TeamLead;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeRepositoryTest extends AbstractRepositoryTestCase {

    @Test
    public void canCreateEmployee() {
        createEmployee(7);

        Employee employeeByName = employeeRepository.findByName(employee.username()).get(0);
        assertThat(employeeByName.username(), is("Pedr"));
        assertThat(employeeByName.role(), is(Developer));
        assertThat(employeeByName.skills().size(), is(1));
        assertThat(employeeByName.skills().get(0).value(), is(7));
        assertThat(employeeByName.skills().get(0).description(), is("Functional Delivery"));
        assertThat(employeeByName.skills().get(0).comment(), is("Always delivers on time"));
    }

    @Test
    public void canUpdateEmployee() {
        createEmployee(7);
        createEmployee(8);

        List<Employee> employees = employeeRepository.findByName(employee.username());
        assertThat(employees.size(), is(2));

        assertThat(employees.get(0).skills().size(), is(1));
        assertThat(employees.get(0).skills().get(0).description(), is("Functional Delivery"));
        assertThat(employees.get(0).skills().get(0).value(), is(8));
        assertThat(employees.get(0).role(), is(Developer));

        assertThat(employees.get(1).skills().size(), is(1));
        assertThat(employees.get(1).skills().get(0).description(), is("Functional Delivery"));
        assertThat(employees.get(1).skills().get(0).value(), is(7));
        assertThat(employees.get(1).role(), is(Developer));
    }

    @Test
    public void canReadEmployee() {
        createEmployee(7);

        Employee readEmployee = employeeRepository.findByName(employee.username()).get(0);

        assertThat(readEmployee.username(), is(employee.username()));
        assertThat(readEmployee.role(), is(employee.role()));
        assertThat(readEmployee.skills().size(), is(employee.skills().size()));
        assertThat(readEmployee.level(), is(employee.level()));
    }

    private void createEmployee(int value) {
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Functional Delivery", value, "Always delivers on time"));
        employee = Employee.developer("Pedr", skills, Level.JuniorDeveloper);

        employeeRepository.create(employee);
    }
}