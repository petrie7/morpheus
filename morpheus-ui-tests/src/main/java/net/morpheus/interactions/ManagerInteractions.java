package net.morpheus.interactions;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Role;
import net.morpheus.stub.LdapStubServer;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
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

    private void loginUser() {
        $(By.id("username")).setValue(employeeForTest.username());
        $(By.id("password")).setValue(employeePassword);
        $(By.id("submit")).click();
    }

    private void openMorpheus() {
        open("http://localhost:1999");
    }
}
