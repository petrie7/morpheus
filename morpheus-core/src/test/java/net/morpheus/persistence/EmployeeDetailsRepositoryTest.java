package net.morpheus.persistence;

import net.morpheus.domain.EmployeeDetails;
import org.junit.Test;

import java.util.Optional;

import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeDetailsRepositoryTest extends AbstractRepositoryTestCase {

    @Test
    public void createEmployee() {
        employee = anEmployeeWithUsername("Pedr");

        employeeDetailsRepository.create(employee);

        Optional<EmployeeDetails> actualEmployee = employeeDetailsRepository.findByName(employee.username());

        assertTrue(actualEmployee.isPresent());
        assertThat(employee, is(actualEmployee.get()));
    }

    @Test
    public void findByUsername() {
        employee = anEmployeeWithUsername("Pedr");
        employee2 = anEmployeeWithUsername("Billy");

        employeeDetailsRepository.create(employee);
        employeeDetailsRepository.create(employee2);

        Optional<EmployeeDetails> actualEmployee = employeeDetailsRepository.findByName(employee.username());

        assertThat(employee, is(actualEmployee.get()));
    }

    @Test
    public void deleteEmployee() {
        employee = anEmployeeWithUsername("Pedr");

        employeeDetailsRepository.create(employee);
        employeeDetailsRepository.delete(employee);

        Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findByName(employee.username());
        assertFalse(employeeDetails.isPresent());
    }

    private EmployeeDetails anEmployeeWithUsername(String username) {
        return anEmployee()
                .withUsername(username)
                .build();
    }

}