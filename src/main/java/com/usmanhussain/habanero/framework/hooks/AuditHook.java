package com.usmanhussain.habanero.framework.hooks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmanhussain.habanero.context.TestContext;
import com.usmanhussain.habanero.framework.StepDefs;
import com.usmanhussain.habanero.framework.element.AuditItem;
import cucumber.api.Scenario;
import cucumber.api.java.After;

import java.io.IOException;

public class AuditHook extends StepDefs {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public AuditHook(TestContext context) {
        super(context);
    }

    @After
    public void after(Scenario scenario) throws IOException {

        for (Object o : getContext().getDataItems()) {

            if (o.getClass().getDeclaredAnnotation(AuditItem.class) != null) {

                byte[] json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsBytes(o);

                scenario.embed(json, "text/plain");
            }
        }
    }
}
