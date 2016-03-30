package net.morpheus.persistence;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import net.morpheus.domain.Employee;
import net.morpheus.domain.Role;
import org.junit.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.HashMap;

import static net.morpheus.config.PersistenceConfig.mongoDbFactory;
import static net.morpheus.domain.Role.Developer;
import static net.morpheus.domain.Role.TeamLead;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeRepositoryTest {

    private EmployeeRepository employeeRepository;
    private Employee employee;
    private static MongodProcess mongod;

    @BeforeClass
    public static void startEmbeddedMongo() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(27017, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        mongod = mongodExecutable.start();
    }

    @AfterClass
    public static void stopMongo() {
        mongod.stop();
    }

    @Before
    public void setup() throws IOException {
        employeeRepository = new EmployeeRepository(new MongoTemplate(mongoDbFactory()));
    }

    @After
    public void clear() {
        employeeRepository.delete(employee);
    }

    @Test
    public void canCreateEmployee() {
        createEmployee();

        Employee employeeByName = employeeRepository.findByName(employee.username());
        assertThat(employeeByName.username(), is("Pedr"));
        assertThat(employeeByName.role(), is(Developer));
        assertThat(employeeByName.skills().size(), is(1));
        assertThat(employeeByName.skills().get("Functional Delivery"), is(7));
    }

    @Test
    public void canUpdateEmployee() {
        createEmployee();
        employee.addNewSkill("Quality of code", 2);
        employee.setRole(Role.TeamLead);

        employeeRepository.update(employee);

        Employee retrievedEmployee = employeeRepository.findByName(employee.username());
        assertThat(retrievedEmployee.skills().size(), is(2));
        assertThat(retrievedEmployee.skills().get("Quality of code"), is(2));
        assertThat(retrievedEmployee.role(), is(TeamLead));
    }

    private void createEmployee() {
        HashMap<String, Integer> skills = new HashMap<>();
        skills.put("Functional Delivery", 7);
        employee = new Employee("Pedr", Developer, skills);

        employeeRepository.create(employee);
    }
}