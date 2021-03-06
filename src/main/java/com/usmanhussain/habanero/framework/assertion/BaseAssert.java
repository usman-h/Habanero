package com.usmanhussain.habanero.framework.assertion;

import com.usmanhussain.habanero.framework.element.WebItem;
import com.usmanhussain.habanero.framework.page.PageInteractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class BaseAssert implements AssertAction {

    private final AssertType assertType;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public BaseAssert() {
        assertType = AssertType.END_WHEN_OK;
    }

    public BaseAssert(AssertType assertType) {
        this.assertType = assertType;
    }

    @Override
    public void onAction(String action, PageInteractor<?> pageInteractor, Optional<WebItem> item) throws AssertOKException, AssertException {

        if (assertAction(action, pageInteractor, item) && assertType == AssertType.END_WHEN_OK) {
            throw new AssertOKException();
        }
    }

    protected abstract boolean assertAction(String action, PageInteractor<?> pageInteractor, Optional<WebItem> item) throws AssertException;

}
