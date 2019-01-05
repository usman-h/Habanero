package com.usmanhussain.habanero.framework.assertion;

import com.usmanhussain.habanero.framework.page.PageDefinition;

public interface AssertAction {
    void onPageLoad(PageDefinition pageDefinition) throws AssertOKException;
}
