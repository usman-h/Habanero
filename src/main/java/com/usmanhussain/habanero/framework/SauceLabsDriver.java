package com.usmanhussain.habanero.framework;

import com.saucelabs.ci.sauceconnect.SauceConnectFourManager;
import com.saucelabs.ci.sauceconnect.SauceTunnelManager;
import com.usmanhussain.habanero.configuration.SystemProperties;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class SauceLabsDriver extends RemoteWebDriver {

    protected static final Logger LOG = LoggerFactory.getLogger(SauceLabsDriver.class);
    static SauceTunnelManager sauceTunnelManager = new SauceConnectFourManager();
    private static String sauceUser = "USERNAME";
    private static String key = "ACCESS_KEY";
    private static final String SAUCELABS_DRIVER_URL = "http://" + sauceUser + ":" + key + "@ondemand.saucelabs.com:80/wd/hub";

    public SauceLabsDriver(String platform, String browserName, String browserVersion) {
        super(createURl(), caps(platform, browserName, browserVersion));
    }

    public SauceLabsDriver(String os, String browserName, String browserVersion, String appiumVersion, String deviceName, String deviceOrientation) {
        super(createURl(), caps(os, browserName, browserVersion, appiumVersion, deviceName, deviceOrientation));
    }

    private static URL createURl() {
        try {
            return new URL(SAUCELABS_DRIVER_URL);
        } catch (Exception e) {
            LOG.warn(e.getMessage());
        }
        return null;
    }

    private static Capabilities caps(String platform, String browserName, String browserVersion) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            String buildName = SystemProperties.getCurrentShortDate() + " "
                    + WebDriverDiscovery.getBrowserName() + " "
                    + WebDriverDiscovery.getBrowserVersion() + " "
                    + WebDriverDiscovery.getPlatform();

            capabilities.setCapability("browserName", browserName);
            capabilities.setCapability("version", browserVersion);
            capabilities.setCapability("platform", platform);
            capabilities.setCapability("resolution", "1024x768");
            capabilities.setCapability("sauce-advisor", false);
            capabilities.setCapability("record-video", true);
            capabilities.setCapability("record-screenshots", true);
            capabilities.setCapability("build", buildName);

            LOG.info("capabilities : " + capabilities.asMap().toString());
            return capabilities;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Capabilities caps(String platform, String browserName, String appiumVersion, String deviceName, String deviceOrientation, String platformVersion) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            String buildName = SystemProperties.getCurrentShortDate() + " "
                    + WebDriverDiscovery.getBrowserName() + " "
                    + WebDriverDiscovery.getBrowserVersion() + " "
                    + WebDriverDiscovery.getPlatform();
            capabilities.setCapability("appiumVersion", appiumVersion);
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("deviceOrientation", deviceOrientation);
            capabilities.setCapability("browserName", browserName);
            capabilities.setCapability("platformName", platform);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("sauce-advisor", false);
            capabilities.setCapability("record-video", true);
            capabilities.setCapability("record-screenshots", true);
            capabilities.setCapability("build", buildName);

            LOG.info("capabilities : " + capabilities.asMap().toString());
            return capabilities;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void startSauceConnect() {
        try {
            sauceTunnelManager.openConnection(sauceUser, key, 8050, null, "--tunnel-identifier defaultTunnel --no-remove-colliding-tunnels --wait-tunnel-shutdown", null, null, null);
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

}
