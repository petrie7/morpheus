package net.morpheus;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import net.morpheus.domain.Skill;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

public class DeveloperTest extends MorpheusTestCase {

    @Test
    public void aDeveloperCanViewTheirSkillsAndComments() throws Exception {
        given(anUserExists());
        and(hasSomeSkillsWithComments());

        when(theUserLogsIn());

        then(theSkillsMatrix(), isDisplayed());
        then(theSkillsMatrix(), containsAllEmployeesSkillsWithComments());
    }

    private GivensBuilder hasSomeSkillsWithComments() {
        return interestingGivens -> {
            employeeRepository.create(employeeForTest);
            return interestingGivens;
        };
    }

    private StateExtractor<List<WebElement>> theSkillsMatrix() {
        return capturedInputAndOutputs1 -> {
            WebElement table = webDriver.findElements(By.id("myTable")).get(0);
            new WebDriverWait(webDriver, 7)
                    .until((WebDriver driver) -> table.findElements(By.tagName("tr")).size() > 3);

            return table.findElements(By.tagName("tr"));
        };
    }

    private Matcher<List<WebElement>> isDisplayed() {
        return new TypeSafeMatcher<List<WebElement>>() {
            @Override
            protected boolean matchesSafely(List<WebElement> rows) {
                return !rows.isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Table was not present");
            }
        };
    }

    private Matcher<List<WebElement>> containsAllEmployeesSkillsWithComments() {
        return new TypeSafeMatcher<List<WebElement>>() {
            @Override
            protected boolean matchesSafely(List<WebElement> rows) {
                for (Skill skill : employeeForTest.skills()) {
                    Optional<WebElement> matchingSkill = rows.stream().filter(r -> r.getText().contains(skill.description())
                            && ((RemoteWebElement) r).findElementByName("commentText").getAttribute("value").contains(skill.comment())
                    ).findFirst();
                    if (!matchingSkill.isPresent()) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("All " + employeeForTest.skills() + " were not present");
            }
        };
    }

}
