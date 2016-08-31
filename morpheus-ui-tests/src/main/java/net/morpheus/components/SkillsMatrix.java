package net.morpheus.components;

import com.google.common.base.Predicate;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SkillsMatrix {

    private WebDriver webDriver;

    public SkillsMatrix(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public StateExtractor<List<WebElement>> entries() {
        return capturedInputAndOutputs1 -> {
            new WebDriverWait(webDriver, 30).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver webDriver) {
                    return webDriver.findElement(By.className("myTable")).isDisplayed();
                }
            });

            WebElement table = webDriver.findElements(By.className("myTable")).get(0);
            return table.findElements(By.tagName("tr"));
        };
    }
}
