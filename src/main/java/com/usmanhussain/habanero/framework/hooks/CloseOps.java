package com.usmanhussain.habanero.framework.hooks;

import com.usmanhussain.habanero.context.TestContext;
import cucumber.api.Scenario;

public interface CloseOps {

    void close(Scenario scenario, TestContext context);

}