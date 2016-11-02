package net.morpheus.persistence;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.EmployeeRecord;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

import static net.morpheus.config.PersistenceConfig.mongoDbFactory;

public abstract class AbstractRepositoryTestCase {

    private static MongodProcess mongod;

    protected EmployeeRecordRepository employeeRecordRepository;
    protected SkillTemplateRepository skillTemplateRepository;
    protected EmployeeRepository employeeDetailsRepository;

    protected EmployeeRecord employeeRecord;
    protected EmployeeDetails employee;
    protected EmployeeDetails employee2;

    @BeforeClass
    public static void startEmbeddedMongo() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(27018, Network.localhostIsIPv6()))
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
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        employeeRecordRepository = new EmployeeRecordRepository(mongoTemplate);
        employeeDetailsRepository = new EmployeeRepository(mongoTemplate);
        skillTemplateRepository = new SkillTemplateRepository(mongoTemplate);
    }

    @After
    public void clear() {
        if (employee != null) {
            employeeDetailsRepository.delete(employee);
        }
        if (employee2 != null) {
            employeeDetailsRepository.delete(employee);
        }
        if (employeeRecord != null) {
            employeeRecordRepository.delete(employeeRecord);
        }
    }

}
