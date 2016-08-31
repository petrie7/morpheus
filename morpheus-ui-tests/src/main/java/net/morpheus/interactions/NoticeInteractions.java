package net.morpheus.interactions;

import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NoticeInteractions {

    private WebDriver webDriver;

    public NoticeInteractions(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public StateExtractor<WebElement> ofSuccess() {
        return capturedInputAndOutputs -> {
            new WebDriverWait(webDriver, 3)
                    .until((WebDriver driver) -> webDriver.findElement(By.className("growl-notice")).isDisplayed());
            return webDriver.findElement(By.className("growl-notice"));
        };
    }

    public StateExtractor<WebElement> ofError() {
        return capturedInputAndOutputs -> {
            new WebDriverWait(webDriver, 3)
                    .until((WebDriver driver) -> webDriver.findElement(By.className("growl-error")).isDisplayed());
            return webDriver.findElement(By.className("growl-error"));
        };
    }
}
