package net.morpheus;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import net.morpheus.components.SkillsMatrix;
import net.morpheus.components.TymMachine;
import net.morpheus.domain.Skill;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

import static net.morpheus.matchers.ElementMatchers.areDisplayed;
import static org.hamcrest.Matchers.equalTo;


public class DeveloperTest extends MorpheusTestCase {

    private SkillsMatrix theSkillsMatrix;
    private TymMachine theTymMachine;

    @Before
    public void something() {
        theSkillsMatrix = new SkillsMatrix(webDriver);
        theTymMachine = new TymMachine(webDriver);
    }

    @Test
    public void aDeveloperCanViewTheirSkillsAndComments() throws Exception {
        given(aDeveloperExists());
        and(hasSomeSkillsWithComments());

        when(theDeveloper.logsIn());

        then(theSkillsMatrix.entries(), areDisplayed());
        then(theSkillsMatrix.entries(), containsAllEmployeesSkillsWithComments());
    }

    @Test
    public void noRecordsWhenNoRecordsExistForUser() throws Exception {
        given(aDeveloperExists());

        when(theDeveloper.logsIn());

        then(theSkillsMatrix.entries(), areDisplayed());
        then(theTymMachine.message(), equalTo("There are no records to display"));
    }

    private GivensBuilder hasSomeSkillsWithComments() {
        return interestingGivens -> {
            employeeRecordRepository.create(employeeRecordForTest);
            return interestingGivens;
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
