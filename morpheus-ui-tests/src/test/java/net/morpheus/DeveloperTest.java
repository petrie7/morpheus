package net.morpheus;

import com.google.common.base.Predicate;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;


public class DeveloperTest extends MorpheusTestCase {

    @Test
    public void aDeveloperCanViewTheirSkillsAndComments() throws Exception {
        given(anUserExists());
        and(hasSomeSkillsWithComments());

        when(theUserLogsIn());

        then(theSkillsMatrix(), isDisplayed());
        then(theSkillsMatrix(), containsAllEmployeesSkillsWithComments());
    }

    @Test
    public void noRecordsWhenNoRecordsExistForUser() throws Exception {
        given(anUserExists());

        when(theUserLogsIn());

        then(theSkillsMatrix(), isDisplayed());
        then(theTymMachineMessage(), equalTo("There are no records to display"));
    }

    private GivensBuilder hasSomeSkillsWithComments() {
        return interestingGivens -> {
            employeeRecordRepository.create(employeeRecordForTest);
            return interestingGivens;
        };
    }

    private StateExtractor<List<WebElement>> theSkillsMatrix() {
        return capturedInputAndOutputs1 -> {
            new WebDriverWait(webDriver, 30).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver webDriver) {
                    return webDriver.findElement(By.className("myTable")).isDisplayed();
                }
            });

            WebElement table = webDriver.findElements(By.className("myTable")).get(0);
            return table.findElements(By.tagName("tr"));
        };
    }

    private StateExtractor<String> theTymMachineMessage() {
        return inputAndOutputs -> webDriver.findElement(By.id("date")).getText();
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
                for (Skill skill : employeeRecordForTest.skills()) {
                    Optional<WebElement> matchingSkill = rows.stream().filter(r -> r.getText().contains(skill.description())
                            && r.findElement(By.name("commentText")).getAttribute("value").contains(skill.comment())
                    ).findFirst();
                    if (!matchingSkill.isPresent()) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("All " + employeeRecordForTest.skills() + " were not present");
            }
        };
    }

}
