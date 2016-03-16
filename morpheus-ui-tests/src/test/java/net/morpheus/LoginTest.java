package net.morpheus;

import com.codeborne.selenide.WebDriverRunner;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public class LoginTest extends MorpheusTestCase {

    @Test
    public void userCanLogin() throws Exception {
        given(anUserExists());

        when(theUserLogsIn());

        then(theDisplayedPage(), isHome());
    }

    private StateExtractor<String> theDisplayedPage() {
        return capturedInputAndOutputs -> WebDriverRunner.getWebDriver().getCurrentUrl();
    }

    private Matcher<String> isHome() {
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String actualUrl) {
                return actualUrl.equals("http://localhost:1999/");
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }
}
