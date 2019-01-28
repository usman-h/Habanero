package com.usmanhussain.habanero.framework;

import com.usmanhussain.habanero.context.TestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StepDefs {

    private final TestContext context;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public StepDefs(TestContext context) {
        this.context = context;
    }

    protected TestContext getContext() {
        return context;
    }

}