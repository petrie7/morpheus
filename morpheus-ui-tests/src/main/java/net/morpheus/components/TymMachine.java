package net.morpheus.components;

import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TymMachine {

    private WebDriver webDriver;

    public TymMachine(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public StateExtractor<String> message() {
        return inputAndOutputs -> webDriver.findElement(By.id("date")).getText();
    }
}
