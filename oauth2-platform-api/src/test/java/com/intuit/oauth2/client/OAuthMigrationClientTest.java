/*******************************************************************************
 * Copyright (c) 2019 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.oauth2.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.config.OAuth2Config.OAuth2ConfigBuilder;
import com.intuit.oauth2.config.ProxyConfig;
import com.intuit.oauth2.config.Scope;
import com.intuit.oauth2.data.OAuthMigrationRequest;
import com.intuit.oauth2.data.OAuthMigrationResponse;
import com.intuit.oauth2.exception.ConnectionException;
import com.intuit.oauth2.http.Response;
import com.intuit.oauth2.utils.MapperImpl;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.nio.charset.StandardCharsets;
import static org.testng.Assert.assertEquals;

public class OAuthMigrationClientTest {

    public static final ObjectMapper mapper = MapperImpl.getInstance();
    public static final String intuit_tid = "abcd-123-xyz";
    private ProxyConfig proxyConfig;

    private OAuthMigrationClient oAuth2MigrationClient;
    private OAuth2Config oauth2Config;
    private OAuthMigrationRequest oAuthMigrationRequest;
    private MockedHttpRequestClient mockedHttpRequestClient;

    public OAuthMigrationClientTest() {

        oauth2Config = new OAuth2ConfigBuilder("test-client", "test-secret")
                .proxyConfig(proxyConfig).buildConfig();

        oAuthMigrationRequest = new OAuthMigrationRequest.OAuthMigrationRequestBuilder(Environment.PRODUCTION, Scope.Accounting)
                .oAuth2Config(oauth2Config)
                .consumerKey("SomeConsumerKey")
                .consumerSecret("SomeConsumerSecret")
                .redirectUri("https://somerandomredirecturi.com/callback")
                .build();

        oAuth2MigrationClient = new OAuthMigrationClient(oAuthMigrationRequest);
    }

    @BeforeClass
    public void setup() {
        proxyConfig = new ProxyConfig.ProxyConfigBuilder("test-host", "8080")
                .username("username").password("password").domain("test-domain").buildConfig();
        mockedHttpRequestClient = new MockedHttpRequestClient();
    }

    @Test
    public void testMigrate() throws Exception {

        ObjectWriter writer = mapper.writerFor(OAuthMigrationResponse.class);

        OAuthMigrationResponse expectedOAuthMigrationResponse = new OAuthMigrationResponse();
        expectedOAuthMigrationResponse.setAccessToken("eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiZGlyIn0..2T42Mngmoyk44UniOVFSgw.Hl3mqFm4Mq92Ux1m2UTIT0lKgNIF1QpM-NpedlZpxTKsZrI5EJ-amu8K24aM1GP0s3Ul3_ryyZiKnncs407v6c-2Z8mjwDJlYH_2iHs_MgF7epcmuZE8pjr_0ICx2ciYxDO1xA0IFwAT5db2D4ZRzkCNbpijXn6nuh0HJKLQyB4J53rVNLn-0yymQ1Jua9n9ZwmQ36uB1b1lSVTc16eE286h1fMKHcI6k9kGvv93kiWbPasntyRwy7ADj0ZX-Ct-syzoSrTuvChICKIgXc3N7zYfDZi_HM9viFNSOSnJ3-B_ysaZCbdIb8jgrCENwY3Pplp10hvpNIP3JCNl6GZktiuYW-o4shtkMQaWg-0OgnWdCI94Q52Ux32uun9ds8RpGWVTN3Ln9kzVg5lnNrNS9Ll6qm7yGLAmhZH9STgvL0eBPGXKLdg3Nku6cXGB26L8gN69S-r5k99d9xPuTy3H-qq8p2ebId9ys8nVlXshryFz48lBq0Tza5YZ3xqcyNoCs-GgEzZ2yS-v9mpK53BMh6ikggeAdPR4fBx7LTD1xL4I3ED8RrrzkC1mxdrTYoA6xNzTEt979PgSbVlCRj64F9Uft5GQ6ktdV0trWdPiSaLFb7EtjXzqYmduIdN_tEx2x8paefXRsp3X078klbDWb6kLTgamLlh_veOF7e3WXC9NQgIvFfQHfCIPDjooiaYcObolm0soE0j8yO0IoMZOEg.Z-nc_cRHkEA4V2qZJ_HnTg");
        expectedOAuthMigrationResponse.setRefreshToken("AB11581303147XhI4cHvxvcnBo14kMgYIknAB6aRSVs2DO7Bv0");
        expectedOAuthMigrationResponse.setExpiresIn(3600L);
        expectedOAuthMigrationResponse.setTokenType("bearer");
        expectedOAuthMigrationResponse.setRealmId("123147196581394");
        expectedOAuthMigrationResponse.setXRefreshTokenExpiresIn(8726400L);

        Response mockResponse = new Response(
                IOUtils.toInputStream(writer.writeValueAsString(expectedOAuthMigrationResponse), StandardCharsets.UTF_8), 200, intuit_tid);

        // Set the mocked response against the mocked HTTP Client
        mockedHttpRequestClient.setMockResponse(mockResponse);

        OAuthMigrationResponse actualOAuthMigrationResponse = oAuth2MigrationClient.migrate();

        assertEquals(expectedOAuthMigrationResponse.getAccessToken(), actualOAuthMigrationResponse.getAccessToken());
        assertEquals(expectedOAuthMigrationResponse.getRefreshToken(), actualOAuthMigrationResponse.getRefreshToken());
        assertEquals(expectedOAuthMigrationResponse.getExpiresIn(), actualOAuthMigrationResponse.getExpiresIn());
        assertEquals(expectedOAuthMigrationResponse.getTokenType(), actualOAuthMigrationResponse.getTokenType());
        assertEquals(expectedOAuthMigrationResponse.getRealmId(), actualOAuthMigrationResponse.getRealmId());
        assertEquals(expectedOAuthMigrationResponse.getXRefreshTokenExpiresIn(), actualOAuthMigrationResponse.getXRefreshTokenExpiresIn());
    }

    @Test(expectedExceptions = ConnectionException.class)
    public void testMigrateInvalidResponse() throws Exception {

        ObjectWriter writer = mapper.writerFor(OAuthMigrationResponse.class);

        Response mockResponse = new Response(
                IOUtils.toInputStream(writer.writeValueAsString(new OAuthMigrationResponse()), StandardCharsets.UTF_8), 500, intuit_tid);

        // Set the mocked response against the mocked HTTP Client
        mockedHttpRequestClient.setMockResponse(mockResponse);

        OAuthMigrationResponse actualOAuthMigrationResponse = oAuth2MigrationClient.migrate();
    }
}