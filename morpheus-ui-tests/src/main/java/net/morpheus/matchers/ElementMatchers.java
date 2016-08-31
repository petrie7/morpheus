package net.morpheus.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementMatchers {

    public static Matcher<WebElement> isDisplayed() {
        return new TypeSafeMatcher<WebElement>() {
            @Override
            protected boolean matchesSafely(WebElement element) {
                return element.isDisplayed();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Web element to be present");
            }
        };
    }

    public static Matcher<List<WebElement>> areDisplayed() {
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
}
