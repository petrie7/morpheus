package net.morpheus.interactions;

import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Role;
import net.morpheus.stub.LdapStubServer;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;

public class TeamLeadInteractions {
    private final LdapStubServer ldapStubServer;
    private final String employeePassword;
    private EmployeeDetails employeeForTest;

    public TeamLeadInteractions(LdapStubServer ldapStubServer, String employeePassword) {
        this.ldapStubServer = ldapStubServer;
        this.employeePassword = employeePassword;
    }

    public GivensBuilder isLoggedIn() {
        return interestingGivens -> {
            employeeForTest = anEmployee()
                    .withUsername("TeamLead")
                    .withRole(Role.TeamLead)
                    .build();

            ldapStubServer.addEmployee(employeeForTest, employeePassword);

            openMorpheus();
            loginUser();
            return interestingGivens;
        };
    }

    public GivensBuilder isViewing(String username) {
        return interestingGivens -> {
            // search for and click on link with username
            return interestingGivens;
        };
    }

    public ActionUnderTest entersAManagerCommentWith(String someComment) {
        return null;
    }

    public ActionUnderTest savesTheTemplate() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("save-button")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest viewsTheDevComment() {
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
}
