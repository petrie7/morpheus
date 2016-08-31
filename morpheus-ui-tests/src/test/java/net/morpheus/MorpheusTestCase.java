package net.morpheus;

import com.codeborne.selenide.WebDriverRunner;
import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import net.morpheus.config.MorpheusApplicationConfig;
import net.morpheus.config.PersistenceConfig;
import net.morpheus.domain.*;
import net.morpheus.interactions.ManagerInteractions;
import net.morpheus.interactions.NoticeInteractions;
import net.morpheus.interactions.UserInteractions;
import net.morpheus.persistence.EmployeeRecordRepository;
import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.persistence.SkillTemplateRepository;
import net.morpheus.persistence.TeamRepository;
import net.morpheus.stub.LdapStubServer;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestContextManager;

import java.util.ArrayList;

import static com.googlecode.yatspec.internal.totallylazy.$Sequences.sequence;
import static java.util.Arrays.asList;
import static net.morpheus.MorpheusDataFixtures.someString;
import static net.morpheus.MorpheusDataFixtures.someTeam;
import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;
import static net.morpheus.domain.builder.EmployeeRecordBuilder.anEmployeeRecord;
import static net.morpheus.domain.builder.TemplateFieldBuilder.templateField;

@RunWith(SpecRunner.class)
@SpringApplicationConfiguration(classes = {MorpheusApplicationConfig.class, PersistenceConfig.class})
@WebIntegrationTest("spring.main.show_banner=false")
public abstract class MorpheusTestCase extends TestState implements WithCustomResultListeners {

    @Autowired
    protected EmployeeRecordRepository employeeRecordRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    protected TeamRepository teamRepository;

    @Autowired
    private SkillTemplateRepository skillTemplateRepository;
    protected LdapStubServer ldapStubServer;

    protected WebDriver webDriver;
    protected EmployeeDetails employeeDetailsForTest;
    protected EmployeeRecord employeeRecordForTest;
    private EmployeeDetails employeeForTest;
    private String employeePassword;

    private MongoStub mongoStub;
    protected ManagerInteractions aManager;
    protected UserInteractions theUser;
    protected NoticeInteractions aNotice;

    @Before
    public void setupTest() throws Exception {
        TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        mongoStub = new MongoStub();
        mongoStub.start();
        employeeRepositoryTestData();
        skillsRepositoryTestData();

        ldapStubServer = new LdapStubServer();
        ldapStubServer.start();

        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Functional Delivery", 7, "Always delivers on time"));
        String username = someString();
        employeeForTest = anEmployee().withUsername(username).build();
        employeeRecordForTest = anEmployeeRecord().withUsername(username).withSkills(skills).build();
        employeeDetailsForTest = new EmployeeDetails(username, Level.JuniorDeveloper, Role.Developer, someTeam());
        employeePassword = someString();
        webDriver = WebDriverRunner.getWebDriver();

        aManager = new ManagerInteractions(ldapStubServer, employeePassword);
        theUser = new UserInteractions(employeeForTest, employeePassword);
        aNotice = new NoticeInteractions(webDriver);
    }

    private void employeeRepositoryTestData() {
        //Yikes! TODO: Remove this!
        employeeRepository.delete(anEmployee().withUsername("Laurence_Fishburne").build());
        employeeRecordRepository.delete(anEmployeeRecord().withUsername("Laurence_Fishburne").withSkills(null).build());
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Functional Delivery", 5, "Shows Potential"));
        skills.add(new Skill("Quality Of Code", 5, "Fantastic"));
        skills.add(new Skill("Patterns", 5, "Shows Potential"));
        skills.add(new Skill("Refactoring Practice", 5, "Always looks to improve"));
        skills.add(new Skill("Refactoring Experience", 5, "Gaining"));
        skills.add(new Skill("Technical Debt", 5, ""));
        skills.add(new Skill("Estimating and Planning", 5, "Should contribute more"));
        skills.add(new Skill("Design", 5, "Research new patterns"));
        skills.add(new Skill("Solutions", 5, ""));
        skills.add(new Skill("TDD", 5, "Always looking to implement this"));
        skills.add(new Skill("Java", 5, "Continuously Improving"));
        skills.add(new Skill("Database Management Systems", 5, "Needs improving"));

        employeeRepository.create(anEmployee().withUsername("Laurence_Fishburne").build());
        employeeRecordRepository.create(anEmployeeRecord().withUsername("Laurence_Fishburne").withSkills(skills).build());
        //End Yikes!
    }

    private void skillsRepositoryTestData() {
        // Probably not wise TODO
        skillTemplateRepository.deleteAll();

        // More Yikes TODO
        skillTemplateRepository.saveOrUpdate(
                asList(new Template(
                                "Technical Skills",
                                asList(
                                        templateField()
                                                .fieldName("Functional Delivery")
                                                .descriptionForJunior("Should know what Pipeline is")
                                                .descriptionForMid("Should have deployed using Pipeline")
                                                .descriptionForSenior("Should be at one with Pipeline")
                                                .build(),
                                        templateField()
                                                .fieldName("Quality Of Code")
                                                .descriptionForJunior("Should be able to write some code")
                                                .descriptionForMid("Should know what an If Condition is")
                                                .descriptionForSenior("Should know who Uncle Bob is")
                                                .build(),
                                        templateField()
                                                .fieldName("Patterns")
                                                .descriptionForJunior("Knows the difference between a circle and a square")
                                                .descriptionForMid("Knowing that JSON isn't a person")
                                                .descriptionForSenior("Has read GOF Design Patterns cover to cover")
                                                .build()
                                )
                        ),
                        new Template(
                                "Soft Skills",
                                asList(
                                        templateField()
                                                .fieldName("Listening")
                                                .descriptionForJunior("People talking without speaking")
                                                .descriptionForMid("People hearing without listening")
                                                .descriptionForSenior("People writing songs that voices never share")
                                                .build(),
                                        templateField()
                                                .fieldName("Learning (How)")
                                                .descriptionForJunior("Horton knows a How")
                                                .descriptionForMid("Horton know a What")
                                                .descriptionForSenior("Horton knows a Who")
                                                .build(),
                                        templateField()
                                                .fieldName("Learning (What)")
                                                .descriptionForJunior("What is this?")
                                                .descriptionForMid("What is that?")
                                                .descriptionForSenior("What is life?")
                                                .build()
                                )
                        ),
                        new Template(
                                "Business Skills",
                                asList(
                                        templateField()
                                                .fieldName("Functionality")
                                                .descriptionForJunior("Likes Functionality")
                                                .descriptionForMid("Loves Functionality")
                                                .descriptionForSenior("Lives Functionality")
                                                .build(),
                                        templateField()
                                                .fieldName("Business Language / Terminology")
                                                .descriptionForJunior("Knows what stuff is")
                                                .descriptionForMid("Knows what stuff does")
                                                .descriptionForSenior("Knows the meaning of life, the universe and everything")
                                                .build()
                                )
                        )
                )
        );
        // Few
    }

    @After
    public void tearDown() {
        logoutUser();
        ldapStubServer.stop();
        mongoStub.stop();
    }

    protected GivensBuilder anUserExists() {
        return givens -> {
            ldapStubServer.addEmployee(employeeForTest, employeePassword);
            employeeRepository.create(employeeDetailsForTest);
            return givens;
        };
    }

    @Override
    public Iterable<SpecResultListener> getResultListeners() throws Exception {
        return sequence(
                new HtmlResultRenderer())
                .safeCast(SpecResultListener.class);
    }

    private void logoutUser() {
        try {
            webDriver.findElement(By.className("growl-close")).click();
        } catch (Exception e) {
        }
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("document.getElementById('logout-button').click();");
    }
}
