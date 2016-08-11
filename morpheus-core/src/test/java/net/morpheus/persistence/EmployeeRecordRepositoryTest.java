package net.morpheus.persistence;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.domain.Skill;
import net.morpheus.domain.builder.EmployeeRecordBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeRecordRepositoryTest extends AbstractRepositoryTestCase {

    @Test
    public void canCreateEmployeeRecord() {
        createEmployeeRecord(7);

        EmployeeRecord employeeRecordByName = employeeRecordRepository.findByName(employeeRecord.username()).get(0);
        assertThat(employeeRecordByName.username(), is("Pedr"));
        assertThat(employeeRecordByName.skills().size(), is(1));
        assertThat(employeeRecordByName.skills().get(0).value(), is(7));
        assertThat(employeeRecordByName.skills().get(0).description(), is("Functional Delivery"));
        assertThat(employeeRecordByName.skills().get(0).comment(), is("Always delivers on time"));
    }

    @Test
    public void canUpdateEmployee() {
        createEmployeeRecord(7);
        createEmployeeRecord(8);

        List<EmployeeRecord> employeeRecords = employeeRecordRepository.findByName(employeeRecord.username());
        assertThat(employeeRecords.size(), is(2));

        assertThat(employeeRecords.get(0).skills().size(), is(1));
        assertThat(employeeRecords.get(0).skills().get(0).description(), is("Functional Delivery"));
        assertThat(employeeRecords.get(0).skills().get(0).value(), is(8));

        assertThat(employeeRecords.get(1).skills().size(), is(1));
        assertThat(employeeRecords.get(1).skills().get(0).description(), is("Functional Delivery"));
        assertThat(employeeRecords.get(1).skills().get(0).value(), is(7));
    }

    @Test
    public void canReadEmployee() {
        createEmployeeRecord(7);

        EmployeeRecord readEmployeeRecord = employeeRecordRepository.findByName(employeeRecord.username()).get(0);

        assertThat(readEmployeeRecord.username(), is(employeeRecord.username()));
        assertThat(readEmployeeRecord.skills().size(), is(employeeRecord.skills().size()));
    }

    @Test
    public void returnsDistinctUsers() {
        List<EmployeeRecord> employeeRecords = new ArrayList<>();
        employeeRecords.add(EmployeeRecordBuilder.anEmployeeRecord().withUsername("Pedr").build());
        employeeRecords.add(EmployeeRecordBuilder.anEmployeeRecord().withUsername("Pedr").build());
        employeeRecords.add(EmployeeRecordBuilder.anEmployeeRecord().withUsername("Boris").build());

        employeeRecords.forEach(employeeRecord -> employeeRecordRepository.create(employeeRecord));

        List<EmployeeRecord> distinctUsernames = employeeRecordRepository.getAll();

        assertThat(distinctUsernames.size(), is(2));
    }

    private void createEmployeeRecord(int value) {
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Functional Delivery", value, "Always delivers on time"));
        employeeRecord = EmployeeRecordBuilder.anEmployeeRecord().withUsername("Pedr").withSkills(skills).build();

        employeeRecordRepository.create(employeeRecord);
    }
}