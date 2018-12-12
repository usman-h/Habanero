package com.usmanhussain.habanero.context;

import com.usmanhussain.habanero.framework.WebDriverDiscovery;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This context is unique to a scenario, any step def injecting this will have
 * access to the one driver used by the context
 */
public class TestContext {

    private RemoteWebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            driver = WebDriverDiscovery.makeDriver();
        }
        return driver;
    }

    public void close() {
        if (driver != null) {
            driver.close();
        }
    }

}
