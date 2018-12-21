package com.usmanhussain.habanero.context;

import com.usmanhussain.habanero.framework.WebDriverDiscovery;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This context is unique to a scenario, any step def injecting this will have
 * access to the one driver used by the context
 */
public class TestContext {

    private RemoteWebDriver driver;

    private Har har;

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

    public Har getHar() {
        return har;
    }

    public void setHar(Har har) {
        this.har = har;
    }

}