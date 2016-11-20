package net.morpheus.teamlead;

import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.EmployeeRecord;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Ignore;
import org.junit.Test;

import static net.morpheus.MorpheusDataFixtures.someString;
import static net.morpheus.domain.Level.MidDeveloper;

@Ignore
public class TeamLeadTest extends MorpheusTestCase {

    private String someComment = someString();

    @Test
    public void canEditManagerCommentAndViewDevCommentOfTeamMember() throws Exception {
        and(aDeveloperExistsOn(theTeam, MidDeveloper));
        given(theTeamLead.isLoggedIn());
        and(theTeamLead.isViewing(theDeveloper.getEmployeeForTest().username()));

        when(theTeamLead.entersAManagerCommentWith(someComment));
        when(theTeamLead.savesTheTemplate());

        then(theDeveloper(), hasTheComment());

        when(theTeamLead.viewsTheDevComment());

//        then(theDevCommentBox(), isNotEditable());
    }

    private StateExtractor<EmployeeRecord> theDeveloper() {
        return capturedInputAndOutputs1 -> employeeRecordRepository.findByName(employeeDetailsForTest.username()).get(0);
    }

    private Matcher<EmployeeRecord> hasTheComment() {
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
}
