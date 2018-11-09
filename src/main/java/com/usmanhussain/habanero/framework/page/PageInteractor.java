package com.usmanhussain.habanero.framework.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageInteractor<T extends PageDefinition> {

    private static final int DEFAULT_CURSOR_TIME_OUT_SECS = 120;

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
}
