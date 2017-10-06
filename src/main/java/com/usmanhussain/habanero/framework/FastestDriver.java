package com.usmanhussain.habanero.framework;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class FastestDriver extends RemoteWebDriver {

    protected static final Logger logger = LoggerFactory.getLogger(FastestDriver.class);
    private static final String FASTEST_DRIVER_URL = "http://fastest.cognizant.com/wd/hub";

    public FastestDriver(String platform, String browserName, String browserVersion, String fastestUser, String key, String requestId) {
        super(createURl(), caps(platform, browserName, browserVersion, fastestUser, key, requestId));
    }

    private static URL createURl() {
        try {
            return new URL(FASTEST_DRIVER_URL);
        } catch (Exception e) {
            logger.warn("context", e);
        }
        return null;
    }

    private static Capabilities caps(String platform, String browserName, String browserVersion, String fastestUser, String fastestKey, String requestId) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            String buildName = WebDriverDiscovery.getBrowserName() + "-"
                    + WebDriverDiscovery.getBrowserVersion() + "-"
                    + WebDriverDiscovery.getPlatform();
            capabilities.setCapability("username", fastestUser);
            capabilities.setCapability("password", fastestKey);
            capabilities.setCapability("fastpaas.local", true);
            capabilities.setCapability("browserName", browserName);
            capabilities.setCapability("version", browserVersion);
            capabilities.setCapability("platform", platform);
            capabilities.setCapability("packagename", buildName);
            capabilities.setCapability("servicerequestid", requestId);
            logger.info("capabilities : " + capabilities.asMap().toString());
            return capabilities;
        } catch (Exception e) {
            logger.warn("context", e);
        }
        return null;
    }


}
