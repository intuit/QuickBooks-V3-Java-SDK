package com.intuit.payment.config;

import org.testng.Assert;
import org.testng.annotations.Test;

/*******************************************************************************
 * Copyright (c) 2019 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author praveenadg
 *******************************************************************************/

public class ProxyConfigTest {

    private static final String HOST = "https://github.com";
    private static final String PORT = "8080";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DOMAIN = "domain";

    private ProxyConfig init(String host, String port, String username, String password, String domain) {
        ProxyConfig.ProxyConfigBuilder proxyConfigBuilder = new ProxyConfig.ProxyConfigBuilder(host, port);
        proxyConfigBuilder.username(username);
        proxyConfigBuilder.password(password);
        proxyConfigBuilder.domain(domain);
        return proxyConfigBuilder.buildConfig();

    }

    @Test
    public void testAllGetters() {
        ProxyConfig proxyConfig = init(HOST, PORT, USERNAME, PASSWORD, DOMAIN);
        Assert.assertEquals(proxyConfig.getHost(), HOST);
        Assert.assertEquals(proxyConfig.getPort(), PORT);
        Assert.assertEquals(proxyConfig.getUsername(), USERNAME);
        Assert.assertEquals(proxyConfig.getPassword(), PASSWORD);
        Assert.assertEquals(proxyConfig.getDomain(), DOMAIN);
    }
}
