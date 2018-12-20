package com.usmanhussain.habanero.framework.hooks;

import com.usmanhussain.habanero.context.HarContext;
import com.usmanhussain.habanero.context.TestContext;
import com.usmanhussain.habanero.framework.WebDriverDiscovery;
import cucumber.api.Scenario;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;

public class CloseScreenshot implements CloseOps {

    protected static final Logger LOG = LoggerFactory.getLogger(CloseHook.class);

    @Override
    public void close(Scenario scenario, TestContext context) {
        if (scenario.isFailed()) {
            try {
                LOG.info("Scenario FAILED... screen shot taken");
                scenario.write(context.getDriver().getCurrentUrl());
                scenario.write(context.getDriver().getPageSource());
                byte[] screenShot = ((TakesScreenshot) context.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenShot, "image/png");
                String pageSource = context.getDriver().getPageSource().replace("html", "");
                PrintWriter out = new PrintWriter("target/cucumber_reports/" + scenario.getName().replace("/", "").replace(" ", "") + ".html");
                out.println(pageSource);
                scenario.embed(pageSource.getBytes(), "text/plain");
                Har har = context.getHar();
                File harFile = new File("target/cucumber_reports/" + scenario.getName().replace("/", "").replace(" ", "") + ".har");
                har.writeTo(harFile);
            } catch (Exception e) {
                LOG.error("Usman waz here", e.getMessage());
            }
        }
    }
}
