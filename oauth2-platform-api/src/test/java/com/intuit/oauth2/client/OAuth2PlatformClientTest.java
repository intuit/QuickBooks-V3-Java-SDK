/*******************************************************************************
 * Copyright (c) 2017 Intuit
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


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.config.ProxyConfig;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.data.PlatformResponse;
import com.intuit.oauth2.data.UserInfoResponse;
import com.intuit.oauth2.exception.OAuthException;
import com.intuit.oauth2.exception.OpenIdException;
import com.intuit.oauth2.http.Response;
import com.intuit.oauth2.utils.MapperImpl;

public class OAuth2PlatformClientTest {

  public static final ObjectMapper mapper = MapperImpl.getInstance();
  public static final String AUTH_CODE = "authCode";
  public static final String REDIRECT_URI = "https://4f4390eb.ngrok.io/oauth2redirect";
  public static final String intuit_tid = "abcd-123-xyz";

  private MockedHttpRequestClient mockedHttpRequestClient;
  private OAuth2PlatformClient oAuth2PlatformClient;
  private OAuth2Config oauth2Config;
  private ProxyConfig proxyConfig;

  public OAuth2PlatformClientTest() {

    oauth2Config = new OAuth2Config.OAuth2ConfigBuilder("test-client", "test-secret")
        .proxyConfig(proxyConfig).buildConfig();
    oAuth2PlatformClient = new OAuth2PlatformClient(oauth2Config);

  }

  @BeforeClass
  public void setup() {
    proxyConfig = new ProxyConfig.ProxyConfigBuilder("test-host", "8080")
        .username("username").password("password").domain("test-domain").buildConfig();
    mockedHttpRequestClient = new MockedHttpRequestClient();
  }


  @Test
  public void canRetrieveBearerTokensTest() throws Exception {
    ObjectWriter writer = mapper.writerFor(BearerTokenResponse.class);
    

    BearerTokenResponse mockBTResponse = new BearerTokenResponse();
    mockBTResponse.setAccessToken("access-token");
    mockBTResponse.setRefreshToken("refresh-token");
    mockBTResponse.setExpiresIn(200L);
    mockBTResponse.setTokenType("bearer-token");
    Response mockResponse = new Response(
        IOUtils.toInputStream(writer.writeValueAsString(mockBTResponse), StandardCharsets.UTF_8), 200, intuit_tid);
    mockedHttpRequestClient.setMockResponse(mockResponse);

    BearerTokenResponse bearerTokenResponse = oAuth2PlatformClient.retrieveBearerTokens(AUTH_CODE, REDIRECT_URI);

    assertNotNull(mockedHttpRequestClient.getServiceRequestReceived().getAuthString());
    assertTrue(mockedHttpRequestClient.getServiceRequestReceived().getPostParams()
        .contains(new BasicNameValuePair("grant_type", "authorization_code")));
    assertEquals(mockBTResponse.getAccessToken(), bearerTokenResponse.getAccessToken());
    assertEquals(mockBTResponse.getRefreshToken(), bearerTokenResponse.getRefreshToken());
    assertEquals(mockBTResponse.getExpiresIn(), bearerTokenResponse.getExpiresIn());
    assertEquals(mockBTResponse.getTokenType(), bearerTokenResponse.getTokenType());
  }

  @Test(expectedExceptions = OAuthException.class)
  public void retrieveBearerTokensThrowsOAuthExceptionOnErrorStatus() throws JsonProcessingException, OAuthException {
    ObjectWriter writer = mapper.writerFor(BearerTokenResponse.class);
    Response mockResponse = new Response(
        IOUtils.toInputStream(writer.writeValueAsString(new BearerTokenResponse()), StandardCharsets.UTF_8), 500, intuit_tid);
    mockedHttpRequestClient.setMockResponse(mockResponse);

    oAuth2PlatformClient.retrieveBearerTokens(AUTH_CODE, REDIRECT_URI);
  }

  @Test
  public void canRefreshTokenTest() throws Exception {
    ObjectWriter writer = mapper.writerFor(BearerTokenResponse.class);

    BearerTokenResponse mockBTResponse = new BearerTokenResponse();
    mockBTResponse.setAccessToken("access-token");
    mockBTResponse.setRefreshToken("refresh-token");
    mockBTResponse.setExpiresIn(200L);
    mockBTResponse.setTokenType("bearer-token");
    Response mockResponse = new Response(
        IOUtils.toInputStream(writer.writeValueAsString(mockBTResponse), StandardCharsets.UTF_8), 200, intuit_tid);
    mockedHttpRequestClient.setMockResponse(mockResponse);

    BearerTokenResponse bearerTokenResponse = oAuth2PlatformClient.refreshToken("test-token");

    assertNotNull(mockedHttpRequestClient.getServiceRequestReceived().getAuthString());
    assertTrue(mockedHttpRequestClient.getServiceRequestReceived().getPostParams()
        .contains(new BasicNameValuePair("refresh_token", "test-token")));
    assertEquals(mockBTResponse.getAccessToken(), bearerTokenResponse.getAccessToken());
    assertEquals(mockBTResponse.getRefreshToken(), bearerTokenResponse.getRefreshToken());
    assertEquals(mockBTResponse.getExpiresIn(), bearerTokenResponse.getExpiresIn());
    assertEquals(mockBTResponse.getTokenType(), bearerTokenResponse.getTokenType());
  }

  @Test(expectedExceptions = OAuthException.class)
  public void refreshTokenThrowsOAuthExceptionOnErrorStatus() throws JsonProcessingException, OAuthException {
    ObjectWriter writer = mapper.writerFor(BearerTokenResponse.class);
    Response mockResponse = new Response(
        IOUtils.toInputStream(writer.writeValueAsString(new BearerTokenResponse()), StandardCharsets.UTF_8), 500, intuit_tid);
    mockedHttpRequestClient.setMockResponse(mockResponse);

    oAuth2PlatformClient.retrieveBearerTokens(AUTH_CODE, REDIRECT_URI);
  }

  @Test
  public void canRevokeTokenTest() throws Exception {

    Response mockResponse = new Response(null, 200, intuit_tid);
    mockedHttpRequestClient.setMockResponse(mockResponse);

    PlatformResponse platformResponse = oAuth2PlatformClient.revokeToken("revoke-token");

    assertNotNull(mockedHttpRequestClient.getServiceRequestReceived().getAuthString());
    assertTrue(mockedHttpRequestClient.getServiceRequestReceived().getPostParams()
        .contains(new BasicNameValuePair("token", "revoke-token")));
    assertEquals(platformResponse.getStatus(), "SUCCESS");
  }

  @Test
  public void returnsErrorStatusOnRevokeTokenFailureTest() throws Exception {

    Response mockResponse = new Response(null, 500, intuit_tid);
    mockedHttpRequestClient.setMockResponse(mockResponse);

    PlatformResponse platformResponse = oAuth2PlatformClient.revokeToken("revoke-token");

    assertNotNull(mockedHttpRequestClient.getServiceRequestReceived().getAuthString());
    assertTrue(mockedHttpRequestClient.getServiceRequestReceived().getPostParams()
        .contains(new BasicNameValuePair("token", "revoke-token")));
    assertEquals(platformResponse.getStatus(), "ERROR");
    assertEquals(platformResponse.getErrorMessage(), "Failed to revoke token");
  }

  @Test
  public void canGetUserInfoTest() throws Exception {

    ObjectWriter writer = mapper.writerFor(UserInfoResponse.class);

    UserInfoResponse mockUserInfoResponse = new UserInfoResponse();
    mockUserInfoResponse.setEmail("abc@xyz.com");

    Response mockResponse = new Response(
        IOUtils.toInputStream(writer.writeValueAsString(mockUserInfoResponse), StandardCharsets.UTF_8), 200, intuit_tid);
    mockedHttpRequestClient.setMockResponse(mockResponse);

    UserInfoResponse userInfoResponse = oAuth2PlatformClient.getUserInfo("test-token");

    assertNotNull(mockedHttpRequestClient.getServiceRequestReceived().getAuthString());
    assertNull(mockedHttpRequestClient.getServiceRequestReceived().getPostParams());
    assertEquals(userInfoResponse.getEmail(), mockUserInfoResponse.getEmail());
  }

  @Test(expectedExceptions = OpenIdException.class)
  public void getUserInfoThrowsOpenIdExceptionOnErrorStatus() throws JsonProcessingException, OpenIdException {
    ObjectWriter writer = mapper.writerFor(UserInfoResponse.class);
    Response mockResponse = new Response(
        IOUtils.toInputStream(writer.writeValueAsString(new UserInfoResponse()), StandardCharsets.UTF_8), 500, intuit_tid);
    mockedHttpRequestClient.setMockResponse(mockResponse);

    oAuth2PlatformClient.getUserInfo("test-token");
  }

  @Test
  public void validateIDTokenReturnsFalseOnInvalidTokenTest() throws OpenIdException {
    String idToken = "eyJraWQiOiIxZTlnZGs3IiwiYWxnIjoiUlMyNTYifQ.ewogImlz\n"
        + "cyI6ICJodHRwOi8vc2VydmVyLmV4YW1wbGUuY29tIiwKICJzdWIiOiAiMjQ4\n"
        + "Mjg5NzYxMDAxIiwKICJhdWQiOiAiczZCaGRSa3F0MyIsCiAibm9uY2UiOiAi\n"
        + "bi0wUzZfV3pBMk1qIiwKICJleHAiOiAxMzExMjgxOTcwLAogImlhdCI6IDEz\n"
        + "MTEyODA5NzAsCiAibmFtZSI6ICJKYW5lIERvZSIsCiAiZ2l2ZW5fbmFtZSI6\n"
        + "ICJKYW5lIiwKICJmYW1pbHlfbmFtZSI6ICJEb2UiLAogImdlbmRlciI6ICJm\n"
        + "ZW1hbGUiLAogImJpcnRoZGF0ZSI6ICIwMDAwLTEwLTMxIiwKICJlbWFpbCI6\n"
        + "ICJqYW5lZG9lQGV4YW1wbGUuY29tIiwKICJwaWN0dXJlIjogImh0dHA6Ly9l\n"
        + "eGFtcGxlLmNvbS9qYW5lZG9lL21lLmpwZyIKfQ.rHQjEmBqn9Jre0OLykYNn\n"
        + "spA10Qql2rvx4FsD00jwlB0Sym4NzpgvPKsDjn_wMkHxcp6CilPcoKrWHcip\n"
        + "R2iAjzLvDNAReF97zoJqq880ZD1bwY82JDauCXELVR9O6_B0w3K-E7yM2mac\n"
        + "AAgNCUwtik6SjoSUZRcf-O5lygIyLENx882p6MtmwaL1hd6qn5RZOQ0TLrOY\n"
        + "u0532g9Exxcm-ChymrB4xLykpDj3lUivJt63eEGGN6DH5K6o33TcxkIjNrCD\n"
        + "4XB1CKKumZvCedgHHF3IAK4dVEDSUoGlH9z4pP_eWYNXvqQOjGs-rDaQzUHl\n"
        + "6cQQWNiDpWOl_lxXjQEvQ";
    assertFalse(oAuth2PlatformClient.validateIDToken(idToken));
  }
}