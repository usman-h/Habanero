package com.usmanhussain.habanero.framework.hooks;


import com.usmanhussain.habanero.context.TestContext;
import com.usmanhussain.habanero.framework.StepDefs;
import com.usmanhussain.habanero.framework.WebDriverDiscovery;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.util.LinkedList;
import java.util.List;

public class CloseHook extends StepDefs {


    private List<CloseOps> closeOps = new LinkedList<>();

    public CloseHook(TestContext context) {
        super(context);
        closeOps.add(new CloseScreenshot());
    }

    /**
     * <p>
     * Maximise browser window before test begins to keep environment consistent
     * </p>
     */
    @Before()
    public void beforeScenario(Scenario scenario) {
        WebDriverDiscovery.server.newHar(scenario.getName());
    }


    @After
    public void close(Scenario scenario) {
        for (CloseOps c : closeOps) {
            c.close(scenario, getContext());
        }
        getContext().close();
    }
}
