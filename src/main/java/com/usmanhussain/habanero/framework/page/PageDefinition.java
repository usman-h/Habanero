package com.usmanhussain.habanero.framework.page;

import com.usmanhussain.habanero.context.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PageDefinition {

    private final TestContext context;

    private static final int ELEMENT_FETCH_TIME_OUT_SECS = 10;

    public PageDefinition(TestContext context) {
        this.context = context;
    }

    protected WebDriver getDriver() {
        return context.getDriver();
    }

    protected WebElement getElementById(String id) {
        return (new WebDriverWait(getDriver(), ELEMENT_FETCH_TIME_OUT_SECS))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    protected WebElement getElementByPath(String path) {
        return (new WebDriverWait(getDriver(), ELEMENT_FETCH_TIME_OUT_SECS))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
    }
}
