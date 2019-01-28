package com.usmanhussain.habanero.framework.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface WebItem {
    WebElement getElement();

    String getName();

    By getBy();
}
