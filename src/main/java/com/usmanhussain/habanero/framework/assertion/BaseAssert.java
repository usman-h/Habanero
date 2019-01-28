package com.usmanhussain.habanero.framework.assertion;

import com.usmanhussain.habanero.framework.element.WebItem;
import com.usmanhussain.habanero.framework.page.PageInteractor;

import java.util.Optional;

public abstract class BaseAssert implements AssertAction {

    private final AssertType assertType;

    public BaseAssert() {
        assertType = AssertType.END_WHEN_OK;
    }

    public BaseAssert(AssertType assertType) {
        this.assertType = assertType;
    }

    @Override
    public void onAction(String action, PageInteractor<?> pageInteractor, Optional<WebItem> item) throws AssertOKException {

        assertAction(action, pageInteractor, item);

        if (assertType == AssertType.END_WHEN_OK) {
            throw new AssertOKException();
        }
    }

    protected abstract void assertAction(String action, PageInteractor<?> pageInteractor, Optional<WebItem> item);

}
