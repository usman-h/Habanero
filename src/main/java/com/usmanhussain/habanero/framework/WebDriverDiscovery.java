package com.usmanhussain.habanero.framework;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

public class WebDriverDiscovery extends EventFiringWebDriver {

    protected static final Logger log = LoggerFactory.getLogger(WebDriverDiscovery.class);
   // public static BrowserMobProxy server;
   public static BrowserMobProxy server = new BrowserMobProxyServer();
    public static RemoteWebDriver remoteWebDriver = makeDriver();
    private static Proxy seleniumProxy ;

    static {

        server.enableHarCaptureTypes(CaptureType.getRequestCaptureTypes());
        server.enableHarCaptureTypes(CaptureType.getResponseCaptureTypes());
        server.start();

        seleniumProxy = ClientUtil.createSeleniumProxy(server);
    }


    public WebDriverDiscovery() {
        super(remoteWebDriver);
    }

    public static RemoteWebDriver getRemoteWebDriver() {
        return remoteWebDriver;
    }

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
                capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
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

    public WebDriver getDriver() {
        return remoteWebDriver;
    }

    @Override
    public void get(String s) {
    }

    @Override
    public String getCurrentUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public List<WebElement> findElements(By by) {
        return null;
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public String getPageSource() {
        return null;
    }

    @Override
    public void close() {
        if (getDriver() != null) {
            getDriver().close();
        }
    }

    @Override
    public void quit() {
    }

    @Override
    public Set<String> getWindowHandles() {
        return null;
    }

    @Override
    public String getWindowHandle() {
        return null;
    }

    @Override
    public TargetLocator switchTo() {
        return null;
    }

    @Override
    public Navigation navigate() {
        return null;
    }

    @Override
    public Options manage() {
        return null;
    }
}
