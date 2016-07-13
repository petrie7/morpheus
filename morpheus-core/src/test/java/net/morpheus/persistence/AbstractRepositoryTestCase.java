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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

import static net.morpheus.config.PersistenceConfig.mongoDbFactory;

public abstract class AbstractRepositoryTestCase {

    private static MongodProcess mongod;

    protected EmployeeRepository employeeRepository;
    protected SkillTemplateRepository skillTemplateRepository;
    protected Employee employee;

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
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        employeeRepository = new EmployeeRepository(mongoTemplate);
        skillTemplateRepository = new SkillTemplateRepository(mongoTemplate);
    }

    @After
    public void clear() {
        if (employee != null) {
            employeeRepository.delete(employee);
        }
    }

}
