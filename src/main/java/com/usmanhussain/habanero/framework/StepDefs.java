package com.usmanhussain.habanero.framework;

import com.usmanhussain.habanero.context.TestContext;

public abstract class StepDefs {

    private final TestContext context;

    public StepDefs(TestContext context) {
        this.context = context;
    }

    protected TestContext getContext() {
        return context;
    }
}
