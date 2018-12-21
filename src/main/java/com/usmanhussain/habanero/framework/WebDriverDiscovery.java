package com.usmanhussain.habanero.framework;

import com.usmanhussain.habanero.context.HarContext;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

public class WebDriverDiscovery {

    protected static final Logger log = LoggerFactory.getLogger(WebDriverDiscovery.class);

    public static RemoteWebDriver makeDriver() {
        switch (System.getProperty("driverType")) {
            case "firefox":
                return new FirefoxDriver();
            case "safari":
                return new SafariDriver();
            case "ie":
                InternetExplorerOptions options = new InternetExplorerOptions();
                options.introduceFlakinessByIgnoringSecurityDomains();
                return new InternetExplorerDriver(options);
            case "chrome":
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(CapabilityType.PROXY, HarContext.getInstance().getSeleniumProxy());
                if (System.getProperty("generateHarReport").equalsIgnoreCase("true"))
                    return new ChromeDriver(capabilities);
                else
                    return new ChromeDriver();
            case "chromeDocker":
                ChromeOptions ChromeOptions = new ChromeOptions();
                String[] chromeOptions = System.getProperty("chromeOptions").split(",");
                for (String option : chromeOptions) {
                    ChromeOptions.addArguments(option);
                }
                return new ChromeDriver(ChromeOptions);
            case "appium":
                try {
                    DesiredCapabilities appiumCapabilities = new DesiredCapabilities();
                    String[] appiumCaps = System.getProperty("capabilities").split(",");
                    for (String cap : appiumCaps) {
                        appiumCapabilities.setCapability(cap.split(":")[0], cap.split(":")[1]);
                    }
                    if (appiumCapabilities.asMap().containsKey("browserName")) {
                        return new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), appiumCapabilities);
                    } else if (appiumCapabilities.getCapability("platformName").toString().equalsIgnoreCase("android")) {
                        return new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), appiumCapabilities);
                    } else {
                        return new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), appiumCapabilities);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            case "docker-grid":
                try {
                    DesiredCapabilities dockerGridCapabilities = new DesiredCapabilities();
                    String driverURL = System.getProperty("driverURL");
                    String[] Caps = System.getProperty("capabilities").split(",");
                    for (String cap : Caps) {
                        dockerGridCapabilities.setCapability(cap.split(":")[0], cap.split(":")[1]);
                    }
                    return new RemoteWebDriver(new URL(driverURL), dockerGridCapabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            case "saucelabs":
                if (getPlatform().contains("iOS") || getPlatform().contains("android")) {
                    return new SauceLabsDriver(getPlatform(), getBrowserName(), getAppiumVersion(), getDeviceName(), getDeviceOrientation(), getPlatformVersion());
                } else {
                    return new SauceLabsDriver(getPlatform(), getBrowserName(), getBrowserVersion());
                }
            case "fastest":
                if (getPlatform().contains("iOS") || getPlatform().contains("android")) {
                    return new FastestDriver(getPlatform(), getBrowserName(), getAppiumVersion(), getDeviceName(), getDeviceOrientation(), getPlatformVersion());
                } else {
                    return new FastestDriver(getPlatform(), getBrowserName(), getBrowserVersion(), getFastestUser(), getFastestPassword(), getRequestKey());
                }
            case "docker":
                try {
                    return new BrowserWebDriverContainer().withDesiredCapabilities(DesiredCapabilities.chrome()).withRecordingMode(RECORD_ALL, new File("target")).getWebDriver();
                } catch (Exception e) {
                    log.warn("context", e);
                    return null;
                }
            default:
                ArrayList<String> cliArgsCap = new ArrayList<String>();
                cliArgsCap.add("--webdriver-loglevel=NONE");
                cliArgsCap.add("--web-security=false");
                cliArgsCap.add("--ssl-protocol=any");
                cliArgsCap.add("--ignore-ssl-errors=true");
                return new PhantomJSDriver();
        }
    }

    public static String getPlatform() {
        return System.getProperty("platform");
    }

    public static String getBrowserVersion() {
        return System.getProperty("browserVersion");
    }

    public static String getBrowserName() {
        return System.getProperty("browserName");
    }

    public static String getFastestUser() {
        return System.getProperty("fastestUser");
    }

    public static String getFastestPassword() {
        return System.getProperty("fastestPassword");
    }

    public static String getRequestKey() {
        return System.getProperty("fastestRequestKey");
    }

    public static String getAppiumVersion() {
        return System.getProperty("appiumVersion");
    }

    public static String getDeviceName() {
        return System.getProperty("deviceName");
    }

    public static String getDeviceOrientation() {
        return System.getProperty("deviceOrientation");
    }

    public static String getPlatformVersion() {
        return System.getProperty("platformVersion");
    }

}