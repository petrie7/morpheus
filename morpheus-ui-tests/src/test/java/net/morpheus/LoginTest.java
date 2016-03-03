package net.morpheus;

import net.morpheus.domain.Employee;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public class LoginTest extends MorpheusTestCase {

    @Test
    public void userCanLogin() throws Exception {
        given(anUserExists());

        when(theUserLogsIn());

        then(theUser(), isLoggedIn());
    }

    private Matcher<Employee> isLoggedIn() {
        return new TypeSafeMatcher<Employee>() {
            @Override
            protected boolean matchesSafely(Employee employee) {
                return employee.equals(employeeForTest);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}
