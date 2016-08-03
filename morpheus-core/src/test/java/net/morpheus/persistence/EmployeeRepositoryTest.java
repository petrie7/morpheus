package net.morpheus.persistence;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.domain.Level;
import net.morpheus.domain.Skill;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static net.morpheus.domain.Role.Developer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeRepositoryTest extends AbstractRepositoryTestCase {

    @Test
    public void canCreateEmployee() {
        createEmployee(7);

        EmployeeRecord employeeRecordByName = employeeRepository.findByName(employeeRecord.username()).get(0);
        assertThat(employeeRecordByName.username(), is("Pedr"));
        assertThat(employeeRecordByName.role(), is(Developer));
        assertThat(employeeRecordByName.skills().size(), is(1));
        assertThat(employeeRecordByName.skills().get(0).value(), is(7));
        assertThat(employeeRecordByName.skills().get(0).description(), is("Functional Delivery"));
        assertThat(employeeRecordByName.skills().get(0).comment(), is("Always delivers on time"));
    }

    @Test
    public void canUpdateEmployee() {
        createEmployee(7);
        createEmployee(8);

        List<EmployeeRecord> employeeRecords = employeeRepository.findByName(employeeRecord.username());
        assertThat(employeeRecords.size(), is(2));

        assertThat(employeeRecords.get(0).skills().size(), is(1));
        assertThat(employeeRecords.get(0).skills().get(0).description(), is("Functional Delivery"));
        assertThat(employeeRecords.get(0).skills().get(0).value(), is(8));
        assertThat(employeeRecords.get(0).role(), is(Developer));

        assertThat(employeeRecords.get(1).skills().size(), is(1));
        assertThat(employeeRecords.get(1).skills().get(0).description(), is("Functional Delivery"));
        assertThat(employeeRecords.get(1).skills().get(0).value(), is(7));
        assertThat(employeeRecords.get(1).role(), is(Developer));
    }

    @Test
    public void canReadEmployee() {
        createEmployee(7);

        EmployeeRecord readEmployeeRecord = employeeRepository.findByName(employeeRecord.username()).get(0);

        assertThat(readEmployeeRecord.username(), is(employeeRecord.username()));
        assertThat(readEmployeeRecord.role(), is(employeeRecord.role()));
        assertThat(readEmployeeRecord.skills().size(), is(employeeRecord.skills().size()));
        assertThat(readEmployeeRecord.level(), is(employeeRecord.level()));
    }

    @Test
    public void returnsDistinctUsers() {
        List<EmployeeRecord> employeeRecords = new ArrayList<>();
        employeeRecords.add(EmployeeRecord.developer("Pedr", new ArrayList<>(), Level.JuniorDeveloper, false));
        employeeRecords.add(EmployeeRecord.developer("Pedr", new ArrayList<>(), Level.JuniorDeveloper, false));
        employeeRecords.add(EmployeeRecord.developer("Boris", new ArrayList<>(), Level.JuniorDeveloper, false));

        employeeRecords.forEach(employeeRecord -> employeeRepository.create(employeeRecord));

        List<EmployeeRecord> distinctUsernames = employeeRepository.getAll();

        assertThat(distinctUsernames.size(), is(2));
    }

    private void createEmployee(int value) {
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Functional Delivery", value, "Always delivers on time"));
        employeeRecord = EmployeeRecord.developer("Pedr", skills, Level.JuniorDeveloper, false);

        employeeRepository.create(employeeRecord);
    }
}