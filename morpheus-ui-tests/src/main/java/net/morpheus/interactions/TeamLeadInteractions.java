package net.morpheus.interactions;

import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import net.morpheus.domain.EmployeeDetails;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TeamLeadInteractions {
    private final String employeePassword;
    private EmployeeDetails employeeForTest;

    public TeamLeadInteractions(EmployeeDetails employeeForTest, String employeePassword) {
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

    public GivensBuilder isViewingTheirOwnMatrix() {
        return interestingGivens -> interestingGivens;
    }

    public GivensBuilder isViewing(String username) {
        return interestingGivens -> {
            $(By.xpath("//*[contains(text(), 'View Team')]")).click();
            $(By.xpath("//*[contains(text(),'" + username + "')]")).click();
            return interestingGivens;
        };
    }

    public ActionUnderTest entersADevCommentWith(String comment) {
        return (interestingGivens, capturedInputAndOutputs) -> {
            $(By.id("devCommentBtn")).click();
            $(By.id("devCommentTextArea")).setValue(comment);
            $(By.xpath("//*[contains(text(), 'Confirm')]")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest entersAManagerCommentWith(String comment) {
        return (interestingGivens, capturedInputAndOutputs) -> {
            $(By.id("managerCommentBtn")).click();
            $(By.id("managerCommentTextArea")).setValue(comment);
            $(By.xpath("//*[contains(text(), 'Confirm')]")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest savesTheTemplate() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("save-button")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest viewsTheDevComment() {
        return (interestingGivens, capturedInputAndOutputs) -> {
            $(By.id("devCommentBtn")).click();
            return capturedInputAndOutputs;
        };
    }

    public ActionUnderTest viewsTheManagerComment() {
        return (interestingGivens, capturedInputAndOutputs) -> {
            $(By.id("managerCommentBtn")).click();
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
