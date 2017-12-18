/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.oauth2.client;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.data.PlatformResponse;
import com.intuit.oauth2.data.UserInfoResponse;
import com.intuit.oauth2.exception.ConnectionException;
import com.intuit.oauth2.exception.OAuthException;
import com.intuit.oauth2.exception.OpenIdException;
import com.intuit.oauth2.http.HttpRequestClient;
import com.intuit.oauth2.http.MethodType;
import com.intuit.oauth2.http.Request;
import com.intuit.oauth2.http.Response;
import com.intuit.oauth2.utils.LoggerImpl;
import com.intuit.oauth2.utils.MapperImpl;

/**
 * Client class for OAuth2 API's with methods to retrieve bearer token,
 * refresh token and get UserInfo
 * 
 * @author dderose
 *
 */
public class OAuth2PlatformClient {
    
    
    private OAuth2Config oauth2Config;

    private static final Logger logger = LoggerImpl.getInstance();
    private static final ObjectMapper mapper  = MapperImpl.getInstance();
    
    public OAuth2PlatformClient(OAuth2Config oauth2Config) {
        this.oauth2Config = oauth2Config;
    }
    
    /**
    * Hiding the default constructor as OAuth2PlatformClient is always required to function properly
    */
   protected OAuth2PlatformClient() {

   }
    
    
    /**
     * Method to retrieve OAuth2 access token by passing the redirectURI and authCode
     * 
     * @param auth_code
     * @param redirectUri
     * @return
     * @throws OAuthException
     */
    public BearerTokenResponse retrieveBearerTokens(String authCode, String redirectURI) throws OAuthException {
        
        logger.debug("Enter OAuth2PlatformClient::retrieveBearerTokens");

        try {
            HttpRequestClient client = new HttpRequestClient(oauth2Config.getProxyConfig());
            Request request = new Request.RequestBuilder(MethodType.POST, oauth2Config.getIntuitBearerTokenEndpoint())
                                            .requiresAuthentication(true)
                                            .authString(getAuthHeader())
                                            .postParams(getUrlParameters(null, authCode, redirectURI))
                                            .build();

            Response response = client.makeRequest(request);
                
            logger.debug("Response Code : "+ response.getStatusCode());
            if (response.getStatusCode() != 200) {
                logger.debug("failed getting access token");
                throw new OAuthException("failed getting access token", response.getStatusCode() + "");
            }
    
            ObjectReader reader = mapper.readerFor(BearerTokenResponse.class);
            BearerTokenResponse bearerTokenResponse = reader.readValue(response.getContent());
            return bearerTokenResponse;
            
        } catch (Exception ex) {
            logger.error("Exception while retrieving bearer tokens", ex);
            throw new OAuthException(ex.getMessage(), ex);
        }
    }
    
    /**
     * Method to renew OAuth2 tokens by passing the refreshToken
     * 
     * @param refreshToken
     * @return
     * @throws OAuthException 
     */
    public BearerTokenResponse refreshToken(String refreshToken) throws OAuthException {
        
        logger.debug("Enter OAuth2PlatformClient::refreshToken");
        try {
            HttpRequestClient client = new HttpRequestClient(oauth2Config.getProxyConfig());
            Request request = new Request.RequestBuilder(MethodType.POST, oauth2Config.getIntuitBearerTokenEndpoint())
                                        .requiresAuthentication(true)
                                        .authString(getAuthHeader())
                                        .postParams(getUrlParameters("refresh", refreshToken, null))
                                        .build();
            Response response = client.makeRequest(request);

            logger.debug("Response Code : "+ response.getStatusCode());
            if (response.getStatusCode() != 200) {
                logger.debug("failed getting access token");
                throw new OAuthException("failed getting access token", response.getStatusCode() + "");
            }
 
            ObjectReader reader = mapper.readerFor(BearerTokenResponse.class);
            BearerTokenResponse bearerTokenResponse = reader.readValue(response.getContent());
            return bearerTokenResponse;
        }
        catch (Exception ex) {
            logger.error("Exception while calling refreshToken ", ex);
            throw new OAuthException(ex.getMessage(), ex);
        } 
    }
    
    
    /**
     * Method to build post parameters
     * 
     * @param action
     * @param token
     * @param redirectUri
     * @return
     */
    private List<NameValuePair> getUrlParameters(String action, String token, String redirectUri) {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        if (action == "revoke") {
            urlParameters.add(new BasicNameValuePair("token", token));
        } else if (action == "refresh") {
            urlParameters.add(new BasicNameValuePair("refresh_token", token));
            urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
        } else {
            urlParameters.add(new BasicNameValuePair("code", token));
            urlParameters.add(new BasicNameValuePair("redirect_uri", redirectUri));
            urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        }
        return urlParameters;
    }

    /**
     * Method to revoke OAuth2 tokens
     * 
     * @param token
     * @return
     * @throws ConnectionException 
     */
    public PlatformResponse revokeToken(String token) throws ConnectionException {

        logger.debug("Enter OAuth2PlatformClient::revokeToken");
        
        PlatformResponse platformResponse = new PlatformResponse();
        try {
            
            HttpRequestClient client = new HttpRequestClient(oauth2Config.getProxyConfig());
            Request request = new Request.RequestBuilder(MethodType.POST, oauth2Config.getIntuitRevokeTokenEndpoint())
                                        .requiresAuthentication(true)
                                        .authString(getAuthHeader())
                                        .postParams(getUrlParameters("revoke", token, null))
                                        .build();

            Response response = client.makeRequest(request);
            
            logger.debug("Response Code : "+ response.getStatusCode());
            if (response.getStatusCode() != 200) {
                logger.debug("failed to revoke token");
                platformResponse.setStatus("ERROR");
                platformResponse.setErrorCode(response.getStatusCode() + "");
                platformResponse.setErrorMessage("Failed to revoke token");
                return platformResponse;
            }
            
            platformResponse.setStatus("SUCCESS");
            return platformResponse;
        }
        catch (Exception ex) {
            logger.error("Exception while calling revokeToken ", ex);
            throw new ConnectionException(ex.getMessage(), ex);
        }    
    }
    
    /**
     * Method to generate auth header based on client ID and Client Secret
     * 
     * @return
     */
    private String getAuthHeader() {
        String base64ClientIdSec = DatatypeConverter.printBase64Binary((oauth2Config.getClientId() + ":" + oauth2Config.getClientSecret()).getBytes());
        return "Basic " + base64ClientIdSec;
    }
    
    /**
     * Method to retrieve UserInfo data associated with the accessToken generated
     * The response depends on the Scope supplied during openId
     * 
     * @param accessToken
     * @return
     * @throws OpenIdException 
     */
    public UserInfoResponse getUserInfo(String accessToken) throws OpenIdException {

        logger.debug("Enter OAuth2PlatformClient::getUserInfo");
        
        try {
            HttpRequestClient client = new HttpRequestClient(oauth2Config.getProxyConfig());
            Request request = new Request.RequestBuilder(MethodType.GET, oauth2Config.getUserProfileEndpoint())
                                        .requiresAuthentication(true)
                                        .authString("Bearer " + accessToken)
                                        .build();
     
            Response response = client.makeRequest(request);

            logger.debug("Response Code : "+ response.getStatusCode());
            if (response.getStatusCode() == 200) {                             
                ObjectReader reader = mapper.readerFor(UserInfoResponse.class);
                UserInfoResponse userInfoResponse = reader.readValue(response.getContent());
                return userInfoResponse;
            } else {
                logger.debug("failed getting user info");
                throw new OpenIdException("failed getting user info", response.getStatusCode() + "");
            }
        }
        catch (Exception ex) {
            logger.error("Exception while retrieving user info ", ex);
            throw new OpenIdException(ex.getMessage(), ex);
        }
        
    }
    
    /**
     * Method to validate IDToken
     * 
     * @param idToken
     * @param clientId
     * @return
     * @throws OpenIdException 
     */
    public boolean validateIDToken(String idToken) throws OpenIdException {
        
        logger.debug("Enter OAuth2PlatformClient::validateIDToken");
        
        String[] idTokenParts = idToken.split("\\.");
        
        if (idTokenParts.length < 3) {
            logger.debug("invalid idTokenParts length");
            return false;
        }

        String idTokenHeader = base64UrlDecode(idTokenParts[0]);
        String idTokenPayload = base64UrlDecode(idTokenParts[1]);
        byte[] idTokenSignature = base64UrlDecodeToBytes(idTokenParts[2]);

        JSONObject idTokenHeaderJson = new JSONObject(idTokenHeader);
        JSONObject idTokenHeaderPayload = new JSONObject(idTokenPayload);

        //Step 1 : First check if the issuer is as mentioned in "issuer" in the discovery doc
        String issuer = idTokenHeaderPayload.getString("iss");
        if(!issuer.equalsIgnoreCase(oauth2Config.getIntuitIdTokenIssuer())) {
            logger.debug("issuer value mismtach");
            return false;
        }

        //Step 2 : check if the aud field in idToken is same as application's clientId
        JSONArray jsonaud = idTokenHeaderPayload.getJSONArray("aud"); 
        String aud = jsonaud.getString(0);

        if(!aud.equalsIgnoreCase(oauth2Config.getClientId())) {
            logger.debug("incorrect client id");
            return false;
        }

        //Step 3 : ensure the timestamp has not elapsed
        Long expirationTimestamp = idTokenHeaderPayload.getLong("exp");
        Long currentTime = System.currentTimeMillis() / 1000; 

        if((expirationTimestamp - currentTime) <= 0) {
            logger.debug("expirationTimestamp has elapsed");
            return false;
        }

        //Step 4: Verify that the ID token is properly signed by the issuer
        HashMap<String,JSONObject> keyMap = getKeyMapFromJWKSUri();
        if (keyMap == null || keyMap.isEmpty()) {
            logger.debug("unable to retrive keyMap from JWKS url");
            return false;
        }
        
        //first get the kid from the header.
        String keyId = idTokenHeaderJson.getString("kid");
        JSONObject keyDetails = keyMap.get(keyId);

        //now get the exponent (e) and modulo (n) to form the PublicKey
        String exponent = keyDetails.getString("e");
        String modulo = keyDetails.getString("n");

        //build the public key
        PublicKey publicKey = getPublicKey(modulo, exponent);

        byte[] data = (idTokenParts[0] + "." + idTokenParts[1]).getBytes(StandardCharsets.UTF_8);

        try {
            //verify token using public key
            boolean isSignatureValid = verifyUsingPublicKey(data, idTokenSignature, publicKey);
            logger.debug("isSignatureValid: " + isSignatureValid);
            return isSignatureValid;
           
        } catch (GeneralSecurityException e) {
            logger.error("Exception while validating ID token ", e);
            throw new OpenIdException(e.getMessage(), e);
        }

    }

    /**
     * Build JWKS keymap
     * 
     * @return
     * @throws OpenIdException
     */
    private HashMap<String, JSONObject> getKeyMapFromJWKSUri() throws OpenIdException {
        
        logger.debug("Enter OAuth2PlatformClient::getKeyMapFromJWKSUri");
        
        try {
            
            HttpRequestClient client = new HttpRequestClient(oauth2Config.getProxyConfig());
            Request request = new Request.RequestBuilder(MethodType.GET, oauth2Config.getIntuitJwksURI())
                    .requiresAuthentication(false)
                    .build();
            
            Response response = client.makeRequest(request);

            logger.debug("Response Code : "+ response.getStatusCode());
            if (response.getStatusCode() != 200) {
                logger.debug("failed JWKS URI");
                throw new OpenIdException("failed JWKS URI", response.getStatusCode() + "");
            }

             return buildKeyMap(response.getContent());
        }
        catch (Exception ex) {
            logger.error("Exception while retrieving jwks ", ex);
            throw new OpenIdException(ex.getMessage(), ex);
        }
    }

    /**
     * Build public key
     * 
     * @param MODULUS
     * @param EXPONENT
     * @return
     */
    private PublicKey getPublicKey(String MODULUS, String EXPONENT) {
        byte[] nb = base64UrlDecodeToBytes(MODULUS);
        byte[] eb = base64UrlDecodeToBytes(EXPONENT);
        BigInteger n = new BigInteger(1, nb);
        BigInteger e = new BigInteger(1, eb);

        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(n, e);
        try {
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(rsaPublicKeySpec);
            return publicKey;
        } catch (Exception ex) {
            logger.error("Exception while getting public key ", ex);
            throw new RuntimeException("Cant create public key", ex);
        }
    }

    /**
     * Verify signature
     * 
     * @param data
     * @param signature
     * @param pubKey
     * @return
     * @throws GeneralSecurityException
     */
    private boolean verifyUsingPublicKey(byte[] data, byte[] signature, PublicKey pubKey)
            throws GeneralSecurityException {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(pubKey);
        sig.update(data);
        return sig.verify(signature);
    }
    
    /**
     * @param input
     * @return
     */
    private String base64UrlDecode(String input) {
        byte[] decodedBytes = base64UrlDecodeToBytes(input);
        String result = new String(decodedBytes, StandardCharsets.UTF_8);
        return result;
    }
    
    /**
     * @param input
     * @return
     */
    private byte[] base64UrlDecodeToBytes(String input) {
        Base64 decoder = new Base64(-1, null, true);
        byte[] decodedBytes = decoder.decode(input);

        return decodedBytes;
    }
    
    /**
     * Build Map from response
     * 
     * @param content
     * @return
     * @throws ConnectionException
     */
    private HashMap<String, JSONObject> buildKeyMap(String content) throws ConnectionException {
        HashMap<String, JSONObject> retMap = new HashMap<String, JSONObject>();
        JSONObject jwksPayload = new JSONObject(content);
        JSONArray keysArray = jwksPayload.getJSONArray("keys");

        for (int i=0;i<keysArray.length();i++) {
            JSONObject object = keysArray.getJSONObject(i);
            String keyId = object.getString("kid");
            retMap.put(keyId,object);
        }
        return retMap;
    }


    
}
