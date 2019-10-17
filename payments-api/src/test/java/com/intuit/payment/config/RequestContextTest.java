package com.intuit.payment.config;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author praveenadg
 */
public class RequestContextTest {
    private static final String ACCESS_TOKEN = "intuit123";
    private static final String REQUEST_ID = "requestId";
    private static final String PROD_BASE_URL = "https://api.intuit.com/quickbooks/v4/payments/";
    private static final String SANDBOX_BASE_URL = "https://sandbox.api.intuit.com/quickbooks/v4/payments/";
    private static final String HOST = "https://github.com";
    private static final String PORT = "8080";

    private RequestContext init(String accessToken, RequestContext.Environment environment, String requestId) {
        RequestContext.Builder builder = new RequestContext.Builder(accessToken, environment);
        builder.requestId(requestId);
        ProxyConfig.ProxyConfigBuilder proxyConfigBuilder = new ProxyConfig.ProxyConfigBuilder(HOST, PORT);
        builder.proxyConfig(proxyConfigBuilder.buildConfig());
        return builder.build();

    }

    @Test
    public void testAllGetters() {
        RequestContext requestContext = init(ACCESS_TOKEN, RequestContext.Environment.SANDBOX, REQUEST_ID);
        Assert.assertEquals(requestContext.getAccessToken(), ACCESS_TOKEN);
        Assert.assertEquals(requestContext.getBaseUrl(), SANDBOX_BASE_URL);
        Assert.assertEquals(requestContext.getRequestId(), REQUEST_ID);
        Assert.assertEquals(requestContext.getProxyConfig().getHost(), HOST);
        Assert.assertEquals(requestContext.getProxyConfig().getPort(), PORT);
    }

    @Test
    public void testAllSetters() {
        RequestContext requestContext = init("", RequestContext.Environment.PRODUCTION, "");
        requestContext.setAccessToken(ACCESS_TOKEN);
        requestContext.setBaseUrl(PROD_BASE_URL);
        requestContext.setRequestId(REQUEST_ID);
        
        ProxyConfig.ProxyConfigBuilder proxyConfigBuilder = new ProxyConfig.ProxyConfigBuilder(HOST, PORT);
        requestContext.setProxyConfig(proxyConfigBuilder.buildConfig());
        
        Assert.assertEquals(requestContext.getAccessToken(), ACCESS_TOKEN);
        Assert.assertEquals(requestContext.getBaseUrl(), PROD_BASE_URL);
        Assert.assertEquals(requestContext.getRequestId(), REQUEST_ID);
        Assert.assertEquals(requestContext.getProxyConfig().getHost(), HOST);
        Assert.assertEquals(requestContext.getProxyConfig().getPort(), PORT);
    }
}
