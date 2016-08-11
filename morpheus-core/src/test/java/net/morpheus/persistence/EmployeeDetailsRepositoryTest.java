package net.morpheus.persistence;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.exception.NoUserExistsException;
import org.junit.Test;

import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeDetailsRepositoryTest extends AbstractRepositoryTestCase {

    @Test
    public void createEmployee() {
        employee = anEmployeeWithUsername("Pedr");

        employeeDetailsRepository.create(employee);

        EmployeeDetails actualEmployee = employeeDetailsRepository.findByName(employee.username());

        assertThat(employee, is(actualEmployee));
    }

    @Test
    public void findByUsername() {
        employee = anEmployeeWithUsername("Pedr");
        employee2 = anEmployeeWithUsername("Billy");

        employeeDetailsRepository.create(employee);
        employeeDetailsRepository.create(employee2);

        EmployeeDetails actualEmployee = employeeDetailsRepository.findByName(employee.username());

        assertThat(employee, is(actualEmployee));
    }

    @Test(expected = NoUserExistsException.class)
    public void deleteEmployee() {
        employee = anEmployeeWithUsername("Pedr");

        employeeDetailsRepository.create(employee);
        employeeDetailsRepository.delete(employee);

        employeeDetailsRepository.findByName(employee.username());
    }

    private EmployeeDetails anEmployeeWithUsername(String username) {
        return anEmployee()
                .withUsername(username)
                .build();
    }

}