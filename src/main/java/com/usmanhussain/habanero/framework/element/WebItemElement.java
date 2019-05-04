package com.usmanhussain.habanero.framework.element;

import com.usmanhussain.habanero.context.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebItemElement implements WebItem {

    //TODO make configurable
    private static final int DEFAULT_ELEMENT_FETCH_TIME_OUT_SECS = 10;

    private final By element;
    private final String name;
    private final TestContext context;
    private final boolean visibleCheck;
    private final int elementFetchTimeOut;

    public WebItemElement(By element, String name, TestContext context, boolean visibleCheck) {
        this(element, name, context, visibleCheck, DEFAULT_ELEMENT_FETCH_TIME_OUT_SECS);
    }

    public WebItemElement(By element, String name, TestContext context, boolean visibleCheck, int elementFetchTimeOut) {

        this.element = element;
        this.name = name;
        this.context = context;
        this.visibleCheck = visibleCheck;
        this.elementFetchTimeOut = elementFetchTimeOut;
    }

    @Override
    public WebElement getElement() {
        if (element != null) {
            return (new WebDriverWait(context.getDriver(), elementFetchTimeOut))
                    .until(visibleCheck ? ExpectedConditions.visibilityOfElementLocated(element) : ExpectedConditions.presenceOfElementLocated(element));
        }

        return null;
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
