package net.morpheus.developer;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.EmployeeRecord;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import static net.morpheus.MorpheusDataFixtures.someString;

public class DeveloperCommentsTest extends MorpheusTestCase {

    private String comment = someString();

    @Test
    public void aDeveloperCanEditTheirComments() throws Exception {
        given(aDeveloperExists());
        and(hasSomeSkillsWithComments());
        and(theDeveloper.isLoggedIn());

        when(theDeveloper.entersAComment(comment));
        when(theDeveloper.savesTheTemplate());

        then(theDeveloper(), hasTheComment());
    }

    private GivensBuilder hasSomeSkillsWithComments() {
        return interestingGivens -> {
            employeeRecordRepository.create(employeeRecordForTest);
            return interestingGivens;
        };
    }

    private StateExtractor<EmployeeRecord> theDeveloper() {
        return capturedInputAndOutputs1 -> employeeRecordRepository.findByName(employeeDetailsForTest.username()).get(0);
    }

    private Matcher<EmployeeRecord> hasTheComment() {
        return new TypeSafeMatcher<EmployeeRecord>() {
            @Override
            protected boolean matchesSafely(EmployeeRecord employeeRecord) {
                return employeeRecord.skills().get(0).devComment().equals(comment);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" comment to be:" + comment);
            }
        };
    }
}
