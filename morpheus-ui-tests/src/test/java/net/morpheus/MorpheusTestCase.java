package net.morpheus;

import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import net.morpheus.config.MorpheusApplicationConfig;
import net.morpheus.domain.Employee;
import net.morpheus.domain.Role;
import net.morpheus.stub.LdapStubServer;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestContextManager;

import java.util.Collections;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.googlecode.yatspec.internal.totallylazy.$Sequences.sequence;
import static net.morpheus.MorpheusDataFixtures.someString;

@RunWith(SpecRunner.class)
@SpringApplicationConfiguration(classes = MorpheusApplicationConfig.class)
@WebIntegrationTest("spring.main.show_banner=false")
public class MorpheusTestCase extends TestState implements WithCustomResultListeners {

    private LdapStubServer ldapStubServer;
    protected Employee employeeForTest;
    private String employeePassword;

    @Before
    public void setupTest() throws Exception {
        TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);

        ldapStubServer = new LdapStubServer();
        ldapStubServer.start();
        employeeForTest = new Employee(someString(), Role.Developer, Collections.emptyMap());
        employeePassword = someString();
    }

    @After
    public void shutDownLdap() {
        ldapStubServer.stop();
    }

    protected GivensBuilder anUserExists() {
        return givens -> {
            ldapStubServer.addEmployee(employeeForTest, employeePassword);
            return givens;
        };
    }

    protected ActionUnderTest theUserLogsIn() {
        return (givens, capturedInputAndOutputs1) -> {
            open("http://localhost:1999");

            $(By.id("username")).setValue(employeeForTest.username());
            $(By.id("password")).setValue(employeePassword);
            $(By.id("submit")).click();
            return capturedInputAndOutputs;
        };
    }

    @Override
    public Iterable<SpecResultListener> getResultListeners() throws Exception {
        return sequence(
                new HtmlResultRenderer())
                .safeCast(SpecResultListener.class);
    }

}
