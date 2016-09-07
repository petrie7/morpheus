package net.morpheus.manager;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Team;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static net.morpheus.MorpheusDataFixtures.someTeam;
import static net.morpheus.domain.Level.MidDeveloper;
import static net.morpheus.domain.Level.SeniorDeveloper;
import static net.morpheus.domain.Role.TeamLead;
import static net.morpheus.matchers.ElementMatchers.isDisabled;
import static net.morpheus.matchers.ElementMatchers.isEnabled;

public class EditEmployeeTest extends MorpheusTestCase {

    private Team anotherTeam;

    @Test
    public void canEditAnEmployee() throws Exception {
        given(aTeamExists());
        and(anotherTeamExists());
        and(theManager.isLoggedIn());
        and(anDeveloperExistsWithLevel(SeniorDeveloper));
        and(theManager.isViewing(theDeveloper.getEmployeeForTest().username()));

        when(theManager.editsTheRoleOfTheDeveloperTo(TeamLead));
        when(theManager.editsTheLevelOfTheDeveloperTo(MidDeveloper));
        when(theManager.editsTheTeamOfTheDeveloperTo(anotherTeam));
        when(theManager.savesTheTemplate());

        then(theDeveloper(), hasALevelOf(MidDeveloper));
        then(theDeveloper(), isOn(anotherTeam));
        then(theDeveloper(), hasARoleOf(TeamLead));
    }

    @Test
    public void canOnlyEditDeveloperRoleOfASeniorDeveloper() throws Exception {
        given(aTeamExists());
        and(theManager.isLoggedIn());
        and(anDeveloperExists());
        and(theManager.isViewing(theDeveloper.getEmployeeForTest().username()));

        then(theEditRoleDropdown(), isDisabled());

        when(theManager.editsTheLevelOfTheDeveloperTo(Level.MidDeveloper));

        then(theEditRoleDropdown(), isDisabled());

        when(theManager.editsTheLevelOfTheDeveloperTo(Level.SeniorDeveloper));

        then(theEditRoleDropdown(), isEnabled());
    }

    private GivensBuilder anotherTeamExists() {
        return interestingGivens -> {
            anotherTeam = someTeam();
            teamRepository.create(anotherTeam);
            return interestingGivens;
        };
    }

    private StateExtractor<EmployeeDetails> theDeveloper() {
        return capturedInputAndOutputs -> employeeRepository.findByName(employeeDetailsForTest.username()).get();
    }

    private StateExtractor<WebElement> theEditRoleDropdown() {
        return capturedInputAndOutputs -> webDriver.findElement(By.id("role-field"));
    }

    private Matcher<EmployeeDetails> hasALevelOf(Level expectedLevel) {
        return new TypeSafeMatcher<EmployeeDetails>() {
            @Override
            protected boolean matchesSafely(EmployeeDetails employeeDetails) {
                return employeeDetails.level().name().equals(expectedLevel.name());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("employee level of: " + expectedLevel);
            }
        };
    }

    private Matcher<EmployeeDetails> isOn(Team expectedTeam) {
        return new TypeSafeMatcher<EmployeeDetails>() {
            @Override
            protected boolean matchesSafely(EmployeeDetails employeeDetails) {
                return employeeDetails.team().name().equals(expectedTeam.name());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("employee team of: " + expectedTeam);
            }
        };
    }

    private Matcher<EmployeeDetails> hasARoleOf(Role expectedRole) {
        return new TypeSafeMatcher<EmployeeDetails>() {
            @Override
            protected boolean matchesSafely(EmployeeDetails employeeDetails) {
                return employeeDetails.role().name().equals(expectedRole.name());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("employee role of: " + expectedRole);
            }
        };
    }

}
