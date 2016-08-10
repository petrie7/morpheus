package net.morpheus.manager;

import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.EmployeeRecord;
import net.morpheus.domain.builder.EmployeeRecordBuilder;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;

public class CreateEmployeeTest extends MorpheusTestCase {

    private EmployeeRecord newEmployeeRecord;

    @Before
    public void setup() {
        newEmployeeRecord = EmployeeRecordBuilder.anEmployeeRecord()
                .withUsername("Pedro")
                .build();
    }

    @Test
    public void canAddNewEmployee() throws Exception {
        given(anEmployeeIsInCauth());
        and(aManagerIsLoggedIn());

        when(theUserNavigatesToCreateEmployee());
        when(entersNewEmployee(newEmployeeRecord));

        then(theNewEmployeeSuccessStatus(), isDisplayed());
//        then(theEmployeeService(), isCalled(once()));
    }

    private GivensBuilder anEmployeeIsInCauth() {
        return givens -> {
            ldapStubServer.addEmployee(newEmployeeRecord, "password");
            return givens;
        };
    }

    private ActionUnderTest theUserNavigatesToCreateEmployee() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("createEmployee")).click();
            return capturedInputAndOutputs;
        };
    }

    private ActionUnderTest entersNewEmployee(EmployeeRecord employeeRecord) {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("newUsername")).setValue(employeeRecord.username());
            $(By.id("roleSelect")).selectOption(employeeRecord.role().toString());
            $(By.id("levelSelect")).selectOption("Junior Developer");
            $(By.id("save-employee")).click();
            return capturedInputAndOutputs;
        };
    }

    private StateExtractor<Object> theEmployeeService() {
        return null;
    }

    private StateExtractor<WebElement> theNewEmployeeSuccessStatus() {
        return capturedInputAndOutputs -> {
            new WebDriverWait(webDriver, 3)
                    .until((WebDriver driver) -> webDriver.findElement(By.className("growl-notice")).isDisplayed());
            return webDriver.findElement(By.className("growl-notice"));
        };
    }

    private Matcher<WebElement> isDisplayed() {
        return new TypeSafeMatcher<WebElement>() {
            @Override
            protected boolean matchesSafely(WebElement element) {
                return element.isDisplayed();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Web element to be present");
            }
        };
    }

    private Matcher<? super Object> isCalled(Object once) {
        return null;
    }

    private void once() {

    }

}
