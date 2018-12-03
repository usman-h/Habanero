package com.usmanhussain.habanero.framework.page;

import com.usmanhussain.habanero.context.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageInteractor<T extends PageDefinition> {

    private static final int DEFAULT_CURSOR_TIME_OUT_SECS = 10;

    private final T pageDefinition;

    public PageInteractor(T pageDefinition) {
        this.pageDefinition = pageDefinition;
    }

    public T getPageDefinition() {
        return pageDefinition;
    }

    protected void press(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    protected WebDriver getDriver() {
        return pageDefinition.getDriver();
    }

    protected void waitUntilElementHidden(By element) {
        waitUntilElementHidden(element, DEFAULT_CURSOR_TIME_OUT_SECS);
    }

    protected void waitUntilElementHidden(By element, int timeoutSecs) {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), timeoutSecs);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    public WebElement waitUntilElementClickable(WebElement element) {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), DEFAULT_CURSOR_TIME_OUT_SECS);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public void selectDropDown(WebElement dropDown, String text) {
        waitUntilElementClickable(dropDown);
        Select select = new Select(dropDown);
        select.selectByVisibleText(text);
    }

    public void selectDropDown(WebElement dropDown, int index) {
        waitUntilElementClickable(dropDown);
        Select select = new Select(dropDown);
        select.selectByIndex(index);
    }

    public void selectDropDownByValue(WebElement dropDown, String text) {
        waitUntilElementClickable(dropDown);
        Select select = new Select(dropDown);
        select.selectByValue(text);
    }

    public TestContext getContext() {
        return getPageDefinition().getContext();
    }
}
