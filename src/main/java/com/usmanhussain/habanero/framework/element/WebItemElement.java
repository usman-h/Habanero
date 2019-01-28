package com.usmanhussain.habanero.framework.element;

import com.usmanhussain.habanero.context.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebItemElement implements WebItem {

    //TODO make configurable
    private static final int ELEMENT_FETCH_TIME_OUT_SECS = 10;

    private final By element;
    private final String name;
    private final TestContext context;

    public WebItemElement(By element, String name, TestContext context) {
        this.element = element;
        this.name = name;
        this.context = context;
    }

    @Override
    public WebElement getElement() {
        return (new WebDriverWait(context.getDriver(), ELEMENT_FETCH_TIME_OUT_SECS))
                .until(ExpectedConditions.presenceOfElementLocated(element));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public By getBy() {
        return element;
    }
}
