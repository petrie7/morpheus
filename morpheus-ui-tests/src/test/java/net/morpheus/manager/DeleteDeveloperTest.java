package net.morpheus.manager;

import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import net.morpheus.MorpheusTestCase;
import net.morpheus.domain.EmployeeDetails;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public class DeleteDeveloperTest extends MorpheusTestCase {

    @Test
    public void canDeleteDeveloper() throws Exception {
        given(aDeveloperExists());
        and(theManager.isLoggedIn());
        and(theManager.isViewing(theDeveloper.getEmployeeForTest().username()));

        when(theManager.clicksDeleteDeveloper());
        when(theManager.confirms());

        then(theDeveloper(), isArchived());
    }

    private StateExtractor<EmployeeDetails> theDeveloper() {
        return capturedInputAndOutputs -> {
            Thread.sleep(50); //reaches here before the isArchived update db call completes
            return employeeRepository.findByName(employeeDetailsForTest.username()).get();
        };
    }

    private Matcher<EmployeeDetails> isArchived() {
        return new TypeSafeMatcher<EmployeeDetails>() {
            @Override
            protected boolean matchesSafely(EmployeeDetails employeeDetails) {
                return employeeDetails.isArchived();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("isArchived to be true");
            }
        };
    }
}
