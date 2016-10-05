package net.morpheus.interactions;

import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import net.morpheus.domain.EmployeeDetails;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeveloperInteractions {

    private EmployeeDetails employeeForTest;
    private String employeePassword;

    public DeveloperInteractions(EmployeeDetails employeeForTest, String employeePassword) {
        this.employeeForTest = employeeForTest;
        this.employeePassword = employeePassword;
    }

    public GivensBuilder isLoggedIn() {
        return interestingGivens -> {
            openMorpheus();
            loginUser();
            return interestingGivens;
        };
    }

    public ActionUnderTest logsIn() {
        return (givens, capturedInputAndOutputs) -> {
            openMorpheus();
            loginUser();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest entersAComment() {
        return null;
    }

    public ActionUnderTest savesTheTemplate() {
        return null;
    }

    private void loginUser() {
        $(By.id("username")).setValue(employeeForTest.username());
        $(By.id("password")).setValue(employeePassword);
        $(By.id("submit")).click();
    }

    private void openMorpheus() {
        open("http://localhost:1999");
    }

    public EmployeeDetails getEmployeeForTest() {
        return employeeForTest;
    }
}
