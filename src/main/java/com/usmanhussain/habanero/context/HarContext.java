package com.usmanhussain.habanero.context;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;

public class HarContext {

    private static HarContext ourInstance = new HarContext();

    private final BrowserMobProxy server = new BrowserMobProxyServer();
    private final Proxy seleniumProxy;

    private HarContext() {
        server.enableHarCaptureTypes(CaptureType.getRequestCaptureTypes());
        server.enableHarCaptureTypes(CaptureType.getResponseCaptureTypes());
        server.start();
        seleniumProxy = ClientUtil.createSeleniumProxy(server);
    }

    public static HarContext getInstance() {
        return ourInstance;
    }

    public Proxy getSeleniumProxy() {
        return seleniumProxy;
    }

    public BrowserMobProxy getServer() {
        return server;
    }

}