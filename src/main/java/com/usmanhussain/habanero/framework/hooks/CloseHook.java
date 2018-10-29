package com.usmanhussain.habanero.framework.hooks;


import com.usmanhussain.habanero.context.TestContext;
import com.usmanhussain.habanero.framework.StepDefs;
import cucumber.api.java.After;

public class CloseHook extends StepDefs {

    public CloseHook(TestContext context) {
        super(context);
    }

    @After
    public void close() {
        getContext().close();
    }
}
