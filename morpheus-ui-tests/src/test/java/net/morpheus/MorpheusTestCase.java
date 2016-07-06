package net.morpheus;

import com.codeborne.selenide.WebDriverRunner;
import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import net.morpheus.config.MorpheusApplicationConfig;
import net.morpheus.domain.Employee;
import net.morpheus.domain.Level;
import net.morpheus.domain.Skill;
import net.morpheus.persistence.EmployeeRepository;
import net.morpheus.stub.LdapStubServer;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestContextManager;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.googlecode.yatspec.internal.totallylazy.$Sequences.sequence;
import static net.morpheus.MorpheusDataFixtures.someString;

@RunWith(SpecRunner.class)
@SpringApplicationConfiguration(classes = MorpheusApplicationConfig.class)
@WebIntegrationTest("spring.main.show_banner=false")
public abstract class MorpheusTestCase extends TestState implements WithCustomResultListeners {

    @Autowired
    protected EmployeeRepository employeeRepository;

    protected LdapStubServer ldapStubServer;

    protected WebDriver webDriver;
    protected Employee employeeForTest;
    private String employeePassword;

    @Before
    public void setupTest() throws Exception {
        TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        ldapStubServer = new LdapStubServer();
        ldapStubServer.start();
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Functional Delivery", 7, "Always delivers on time"));
        employeeForTest = Employee.developer(someString(), skills, Level.JuniorDeveloper);
        employeePassword = someString();
        webDriver = WebDriverRunner.getWebDriver();
    }

    @After
    public void tearDown() {
        logoutUser();
        ldapStubServer.stop();
    }

    protected GivensBuilder anUserExists() {
        return givens -> {
            ldapStubServer.addEmployee(employeeForTest, employeePassword);
            return givens;
        };
    }

    protected GivensBuilder aManagerIsLoggedIn() {
        return interestingGivens1 -> {
            employeeForTest = Employee.manager("Tymbo");
            ldapStubServer.addEmployee(employeeForTest, employeePassword);

            openMorpheus();
            loginUser();
            return interestingGivens1;
        };
    }

    protected ActionUnderTest theUserLogsIn() {
        return (givens, capturedInputAndOutputs1) -> {
            openMorpheus();
            loginUser();
            return capturedInputAndOutputs;
        };
    }

    private void loginUser() {
        $(By.id("username")).setValue(employeeForTest.username());
        $(By.id("password")).setValue(employeePassword);
        $(By.id("submit")).click();
    }

    private void openMorpheus() {
        open("http://localhost:1999");
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
        new WebDriverWait(webDriver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.id("logout-button")));
        webDriver.findElement(By.id("logout-button")).click();
    }
}
