package com.usmanhussain.habanero.framework.element;

import com.usmanhussain.habanero.context.TestContext;
import org.openqa.selenium.By;

public class CompositeWebItem extends WebItemElement {

    private final WebItem associatedItem;

    public CompositeWebItem(By element, String name, TestContext context, WebItem associatedItem, boolean visibleCheck) {
        super(element, name, context, visibleCheck);
        this.associatedItem = associatedItem;
    }

    public WebItem getAssociatedItem() {
        return associatedItem;
    }
}
