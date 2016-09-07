package net.morpheus.interactions;

import com.codeborne.selenide.WebDriverRunner;
import com.google.common.base.Predicate;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Team;
import net.morpheus.stub.LdapStubServer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static net.morpheus.MorpheusDataFixtures.someString;
import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;

public class ManagerInteractions {

    private EmployeeDetails employeeForTest;
    private LdapStubServer ldapStubServer;
    private String employeePassword;

    public ManagerInteractions(LdapStubServer ldapStubServer, String employeePassword) {
        this.ldapStubServer = ldapStubServer;
        this.employeePassword = employeePassword;
    }

    public GivensBuilder isLoggedIn() {
        return interestingGivens -> {
            employeeForTest = anEmployee()
                    .withUsername("Tymbo")
                    .withRole(Role.Manager)
                    .build();

            ldapStubServer.addEmployee(employeeForTest, employeePassword);

            openMorpheus();
            loginUser();
            return interestingGivens;
        };
    }

    public GivensBuilder isViewing(String username) {
        return interestingGivens -> {
            $(By.id("q")).setValue(username);
            $(By.id("search")).click();
            return interestingGivens;
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

    public ActionUnderTest navigatesToEditTemplate() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("editTemplates")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest entersATeamNameOf(Team teamName) {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("teamName")).setValue(teamName.name());
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

    public ActionUnderTest entersANewSkill() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("field-name-textbox")).setValue(someString());
            $(By.id("submit-new-field")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest savesTheTemplate() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("save-button")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest clicksDeleteSkill() {
        return (interestingGivens, capturedInputAndOutputs) -> {
            WebDriverRunner.getWebDriver();
            WebDriverRunner.getWebDriver().findElements(
                    By.xpath("//p[text()=\"Functional Delivery\"]/parent::div/parent::td/parent::tr/td")).get(4)
                    .findElement(By.id("delete-field-button"))
                    .click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest confirms() {
        return (givens, capturedInputAndOutputs) -> {
            new WebDriverWait(WebDriverRunner.getWebDriver(), 3).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver webDriver) {
                    return webDriver.findElement(By.className("modal-footer")).isDisplayed();
                }
            });
            $(By.className("modal-footer")).findElement(By.className("btn-primary")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest editsTheLevelOfTheDeveloperTo(Level level) {
        return (interestingGivens, capturedInputAndOutputs) -> {
            $(By.id("level-field")).setValue(level.name());
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest editsTheTeamOfTheDeveloperTo(Team destinationTeam) {
        return (interestingGivens, capturedInputAndOutputs) -> {
            $(By.id("team-field")).setValue(destinationTeam.name());
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest editsTheRoleOfTheDeveloperTo(Role role) {
        return (interestingGivens, capturedInputAndOutputs) -> {
            $(By.id("role-field")).setValue(role.name());
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
