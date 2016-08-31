package net.morpheus.interactions;

import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Level;
import net.morpheus.domain.Team;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static net.morpheus.MorpheusDataFixtures.someString;

public class UserInteractions {

    private EmployeeDetails employeeForTest;
    private String employeePassword;

    public UserInteractions(EmployeeDetails employeeForTest, String employeePassword) {
        this.employeeForTest = employeeForTest;
        this.employeePassword = employeePassword;
    }

    public ActionUnderTest logsIn() {
        return (givens, capturedInputAndOutputs) -> {
            openMorpheus();
            loginUser();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest navigatesToCreateTeam() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("createTeam")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest navigatesToCreateEmployee() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("createEmployee")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest entersATeamName() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("teamName")).setValue(someString());
            $(By.id("save-team")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest enters(EmployeeDetails newEmployee, Team team) {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("newUsername")).setValue(newEmployee.username());
            $(By.id("roleSelect")).selectOption(newEmployee.role().toString());
            $(By.id("levelSelect")).selectOption(Level.JuniorDeveloper.name());
            $(By.id("teamSelect")).selectOption(team.name());
            $(By.id("save-employee")).click();
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
}
