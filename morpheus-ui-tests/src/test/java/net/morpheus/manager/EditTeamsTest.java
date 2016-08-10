package net.morpheus.manager;

import com.codeborne.selenide.WebDriverRunner;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import net.morpheus.MorpheusTestCase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;

public class EditTeamsTest extends MorpheusTestCase {

    @Test
    public void canMoveEmployeeFromOneTeamToAnother() throws Exception {
        given(aManagerIsLoggedIn());

        when(theManagerNavigatesToEditTeams());

        then(theTeamFor("Pedro"), is("Sonique"));

        when(theManagerMoves("Pedro", toTeam("Lando")));

        then(theTeamFor("Pedro"), is("Lando"));
    }

    private ActionUnderTest theManagerMoves(String teamMember, String targetTeam) {
        return (givens, capturedInputAndOutputs1) -> {
            WebDriver webDriver = WebDriverRunner.getWebDriver();
            new Actions(webDriver).dragAndDrop(
                    webDriver.findElement(By.xpath(format("//*[text()='%s']", teamMember))),
                    webDriver.findElement(By.xpath(format("//div[@id='%s']", targetTeam.toLowerCase())))
            ).perform();
            return capturedInputAndOutputs1;
        };
    }

    private ActionUnderTest theManagerNavigatesToEditTeams() {
        return (givens, capturedInputAndOutputs) -> {
            $(By.id("editTeams")).click();
            return capturedInputAndOutputs;
        };
    }

    private StateExtractor<String> theTeamFor(String teamMember) {
        return inputAndOutputs -> {
            WebDriver webDriver = WebDriverRunner.getWebDriver();
            try {
                //Close alert if there is one!
                webDriver.switchTo().alert().accept();
            } catch (Exception ignore) {
            }
            return webDriver
                    .findElement(By.xpath(format("//*[text()='%s']/parent::div", teamMember)))
                    .findElement(By.className("team-name"))
                    .getText();
        };
    }

    private String toTeam(String targetTeam) {
        return targetTeam;
    }
}
