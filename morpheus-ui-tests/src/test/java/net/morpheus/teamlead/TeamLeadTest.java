package net.morpheus.teamlead;

import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.EmployeeRecord;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.openqa.selenium.By;

import static net.morpheus.MorpheusDataFixtures.someString;
import static net.morpheus.domain.Level.MidDeveloper;

public class TeamLeadTest extends MorpheusTestCase {

    private String someComment = someString();

    @Test
    public void canViewManagerCommentAndEditDevCommentWhenViewingThemselves() throws Exception {
        given(aTeamLeadExistsOn(theTeam));
        and(theTeamLead.isLoggedIn());
        and(theTeamLead.isViewingTheirOwnMatrix());

        when(theTeamLead.entersADevCommentWith(someComment));
        when(theTeamLead.savesTheTemplate());

        then(theTeamLead(), hasTheDevComment());

        when(theTeamLead.viewsTheManagerComment());

        then(theCommentBoxTextArea(), isViewOnly());
    }

    @Test
    public void canEditManagerCommentAndViewDevCommentOfTeamMember() throws Exception {
        given(aTeamLeadExistsOn(theTeam));
        and(aDeveloperExistsOn(theTeam, MidDeveloper));
        and(theTeamLead.isLoggedIn());
        and(theTeamLead.isViewing(theDeveloper.getEmployeeForTest().username()));

        when(theTeamLead.entersAManagerCommentWith(someComment));
        when(theTeamLead.savesTheTemplate());

        then(theDeveloper(), hasTheManagerComment());

        when(theTeamLead.viewsTheDevComment());

        then(theCommentBoxTextArea(), isViewOnly());
    }

    private StateExtractor<String> theCommentBoxTextArea() {
        return inputAndOutputs -> {
            try {
                webDriver.findElement(By.xpath("//*[contains(text(), 'Confirm')]"));
            } catch (Exception e) {
                return "t";
            }
            return "f";
        };
    }

    private StateExtractor<EmployeeRecord> theTeamLead() {
        return inputAndOutputs -> employeeRecordRepository.findByName(teamLeadForTest.username()).get(0);
    }

    private StateExtractor<EmployeeRecord> theDeveloper() {
        return inputAndOutputs -> employeeRecordRepository.findByName(employeeDetailsForTest.username()).get(0);
    }

    private Matcher<EmployeeRecord> hasTheManagerComment() {
        return new TypeSafeMatcher<EmployeeRecord>() {
            @Override
            protected boolean matchesSafely(EmployeeRecord employeeRecord) {
                return employeeRecord.skills().get(0).comment().equals(someComment);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" comment to be:" + someComment);
            }
        };
    }

    private Matcher<EmployeeRecord> hasTheDevComment() {
        return new TypeSafeMatcher<EmployeeRecord>() {
            @Override
            protected boolean matchesSafely(EmployeeRecord employeeRecord) {
                return employeeRecord.skills().get(0).devComment().equals(someComment);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" comment to be:" + someComment);
            }
        };
    }

    private Matcher<String> isViewOnly() {
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String s) {
                return s.equals("t");
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Expected Confirm button not to be present but was");
            }
        };
    }
}
