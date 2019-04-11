package com.usmanhussain.habanero.framework.page;

import com.usmanhussain.habanero.context.TestContext;
import com.usmanhussain.habanero.framework.element.WebItem;
import com.usmanhussain.habanero.framework.element.WebItemElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class PageDefinition {

    private final TestContext context;

    public PageDefinition(TestContext context) {
        this.context = context;
    }

    public TestContext getContext() {
        return context;
    }

    protected WebDriver getDriver() {
        return context.getDriver();
    }

    protected WebItem make(By by, String name) {
        return new WebItemElement(by, name, getContext(), false);
    }

    protected WebItem makeVisible(By by, String name) {
        return new WebItemElement(by, name, getContext(), true);
    }

    protected WebItem make(By by) {
        return new WebItemElement(by, null, getContext(), false);
    }

    protected WebItem makeVisible(By by) {
        return new WebItemElement(by, null, getContext(), true);
    }
}