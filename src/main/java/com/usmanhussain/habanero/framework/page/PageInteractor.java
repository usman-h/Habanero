package com.usmanhussain.habanero.framework.page;

import com.usmanhussain.habanero.context.TestContext;
import com.usmanhussain.habanero.framework.assertion.AssertAction;
import com.usmanhussain.habanero.framework.assertion.AssertConstants;
import com.usmanhussain.habanero.framework.assertion.AssertException;
import com.usmanhussain.habanero.framework.assertion.AssertOKException;
import com.usmanhussain.habanero.framework.element.WebItem;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class PageInteractor<T extends PageDefinition> {

    //TODO if element not present 10 seconds record maxiumum timeout for logs etc - also configure
    private static final int DEFAULT_CURSOR_TIME_OUT_SECS = 10;
    private static final int DEFAULT_FRAME_TIME_OUT_SECS = 50;

    private final T pageDefinition;

    private List<AssertAction> assertActions = new LinkedList<>();

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final String identifier;

    private final List<WebItem> visitedItems = new LinkedList<>();

    public PageInteractor(T pageDefinition, String identifier) {
        this.pageDefinition = pageDefinition;
        this.identifier = identifier;
    }

    public T getPageDefinition() {
        return pageDefinition;
    }

    protected void sendText(WebItem item, CharSequence... txt) throws AssertOKException, AssertException {
        fireBeforeItem(item);

        if (item.getElement() != null) {
            waitUntilElementClickable(item);
            item.getElement().sendKeys(txt);
        }

        fireAfterItem(item);
    }

    protected void press(WebItem item) throws AssertOKException, AssertException {
        fireBeforeItem(item);

        if (item.getElement() != null) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].click();", item.getElement());
        }

        fireAfterItem(item);
    }

    protected void clickItem(WebItem item) throws AssertOKException, AssertException {
        fireBeforeItem(item);

        if (item.getElement() != null) {
            item.getElement().click();
        }

        fireAfterItem(item);
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

    public void waitUntilElementClickable(WebItem item) {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), DEFAULT_CURSOR_TIME_OUT_SECS);
        wait.until(ExpectedConditions.elementToBeClickable(item.getElement()));
    }

    protected void selectDropDown(WebItem item, String text) throws AssertOKException, AssertException {
        fireBeforeItem(item);

        if (item.getElement() != null && text != null) {
            waitUntilElementClickable(item);
            Select select = new Select(item.getElement());
            select.selectByVisibleText(text);
        }

        fireAfterItem(item);
    }

    protected void selectDropDown(WebItem item, Integer index) throws AssertOKException, AssertException {
        fireBeforeItem(item);

        if (item.getElement() != null && index != null) {
            waitUntilElementClickable(item);
            Select select = new Select(item.getElement());
            select.selectByIndex(index);
        }

        fireAfterItem(item);
    }

    protected void selectDropDownByValue(WebItem item, String text) throws AssertOKException, AssertException {
        fireBeforeItem(item);

        if (item.getElement() != null && text != null) {
            waitUntilElementClickable(item);
            Select select = new Select(item.getElement());
            select.selectByValue(text);
        }

        fireAfterItem(item);
    }

    protected void switchToFrame(String frameName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_FRAME_TIME_OUT_SECS);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
    }

    public TestContext getContext() {
        return getPageDefinition().getContext();
    }

    public void addAssert(AssertAction assertAction) {
        assertActions.add(assertAction);
    }

    protected void fireBeforeItem(WebItem item) throws AssertOKException, AssertException {
        fireOnAction(AssertConstants.PRE_ITEM_ACTION, Optional.of(item));
    }

    protected void fireAfterItem(WebItem item) throws AssertOKException, AssertException {
        fireOnAction(AssertConstants.POST_ITEM_ACTION, Optional.of(item));
        visitedItems.add(item);
    }

    protected void fireOnPageLoad() throws AssertOKException, AssertException {
        visitedItems.clear();
        fireOnAction(AssertConstants.PAGE_LOAD_ACTION, Optional.empty());
    }

    protected void fireOnAction(String action, Optional<WebItem> item) throws AssertOKException, AssertException {

        for (AssertAction a : assertActions) {
            a.onAction(action, this, item);
        }
    }

    public List<WebItem> getVisitedItems() {
        return new LinkedList<>(visitedItems);
    }

    public String getIdentifier() {
        return identifier;
    }
}