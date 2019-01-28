package com.usmanhussain.habanero.framework.assertion;

import com.usmanhussain.habanero.framework.element.WebItem;
import com.usmanhussain.habanero.framework.page.PageInteractor;

import java.util.Optional;

public interface AssertAction {
    void onAction(String action, PageInteractor<?> pageInteractor, Optional<WebItem> item) throws AssertOKException;
}
