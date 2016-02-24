package net.morpheus;

import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.*;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpecRunner.class)
public class LoginTest extends TestState {

    @Test
    public void userCanLogin() throws Exception {
        given(anUserExists());

        when(theUserLogsIn());

        then(theUser(), isLoggedIn());
    }

    private GivensBuilder anUserExists() {
        return new GivensBuilder() {
            @Override
            public InterestingGivens build(InterestingGivens givens) throws Exception {
                return null;
            }
        };
    }

    private StateExtractor<Object> theUser() {
        return null;
    }

    private ActionUnderTest theUserLogsIn() {
        return null;
    }

    private Matcher<? super Object> isLoggedIn() {
        return null;
    }
}
