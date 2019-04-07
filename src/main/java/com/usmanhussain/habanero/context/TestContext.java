package com.usmanhussain.habanero.context;

import com.usmanhussain.habanero.framework.WebDriverDiscovery;
import com.usmanhussain.habanero.framework.page.PageInteractor;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This context is unique to a scenario, any step def injecting this will have
 * access to the one driver used by the context
 */
public class TestContext {

    private RemoteWebDriver driver;

    private Har har;

    private Set<PageInteractor> interactors = new LinkedHashSet<>();

    private Set<Object> data = new LinkedHashSet<>();

    private final Logger logger = LoggerFactory.getLogger(getClass());

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

    public PageInteractor<?> getLastInteractor() {
        return interactors.stream().collect(Collectors.toList()).get(interactors.size() - 1);
    }

    public <T extends PageInteractor> T getInteractor(Class<T> type) {

        for (PageInteractor interactor : interactors) {

            if (interactor.getClass().equals(type)) {
                return type.cast(interactor);
            }

        }

        return null;
    }

    public <T extends PageInteractor> T makeInteractor(Class<T> type) {

        T interactor = null;
        try {
            interactor = type.getConstructor(TestContext.class).newInstance(this);
            interactors.add(interactor);
        } catch (Exception e) {
            logger.error("Problem making an interactor", e);
        }

        return interactor;
    }


    public void addDataItem(Object item) {
        data.add(item);
    }

    public <T> T getDataItem(Class<T> type) {

        for (Object d : data) {

            if (d.getClass().equals(type)) {
                return type.cast(d);
            }
        }

        return null;
    }

    public Set<?> getDataItems() {
        return new LinkedHashSet<>(data);
    }
}