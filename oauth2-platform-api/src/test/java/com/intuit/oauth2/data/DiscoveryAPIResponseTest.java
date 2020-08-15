package com.intuit.oauth2.data;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author enzozafra
 */
public class DiscoveryAPIResponseTest {
    private DiscoveryAPIResponse discoveryAPIResponse;

    private String issuer;
    private String authorizationEndpoint;
    private String tokenEndpoint;
    private String userinfoEndpoint;
    private String revocationEndpoint;
    private String jwksUri;

    @BeforeTest
    public void init() {
        issuer = "issuer";
        authorizationEndpoint = "authorizationEndpoint";
        tokenEndpoint = "tokenEndpoint";
        revocationEndpoint = "revocationEndpoint";
        userinfoEndpoint = "userinfoEndpoint";
        jwksUri = "jwksUri";
    }

    @BeforeMethod
    public void setUp() {
        discoveryAPIResponse = new DiscoveryAPIResponse();
        discoveryAPIResponse.setIssuer(issuer);
        discoveryAPIResponse.setAuthorizationEndpoint(authorizationEndpoint);
        discoveryAPIResponse.setTokenEndpoint(tokenEndpoint);
        discoveryAPIResponse.setRevocationEndpoint(revocationEndpoint);
        discoveryAPIResponse.setUserinfoEndpoint(userinfoEndpoint);
        discoveryAPIResponse.setJwksUri(jwksUri);
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(discoveryAPIResponse.getIssuer(), issuer);
        Assert.assertEquals(discoveryAPIResponse.getAuthorizationEndpoint(), authorizationEndpoint);
        Assert.assertEquals(discoveryAPIResponse.getTokenEndpoint(), tokenEndpoint);
        Assert.assertEquals(discoveryAPIResponse.getRevocationEndpoint(), revocationEndpoint);
        Assert.assertEquals(discoveryAPIResponse.getUserinfoEndpoint(), userinfoEndpoint);
        Assert.assertEquals(discoveryAPIResponse.getJwksUri(), jwksUri);
    }

    @Test
    public void testAllSetters() {
        String newIssuer = "321 New Street Address";
        String newAuthorizationEndpoint = "New authorizationEndpoint";
        String newTokenEndpoint = "New TokenEndpoint";
        String newRevocationEndpoint = "New RevocationEndpoint";
        String newUserInfoEndpoint = "New UserinfoEndpoint";
        String newJwksUri = "new JwksUri";

        discoveryAPIResponse.setIssuer(newIssuer);
        discoveryAPIResponse.setAuthorizationEndpoint(newAuthorizationEndpoint);
        discoveryAPIResponse.setTokenEndpoint(newTokenEndpoint);
        discoveryAPIResponse.setRevocationEndpoint(newRevocationEndpoint);
        discoveryAPIResponse.setUserinfoEndpoint(newUserInfoEndpoint);
        discoveryAPIResponse.setJwksUri(newJwksUri);

        Assert.assertEquals(discoveryAPIResponse.getIssuer(), newIssuer);
        Assert.assertEquals(discoveryAPIResponse.getAuthorizationEndpoint(), newAuthorizationEndpoint);
        Assert.assertEquals(discoveryAPIResponse.getTokenEndpoint(), newTokenEndpoint);
        Assert.assertEquals(discoveryAPIResponse.getRevocationEndpoint(), newRevocationEndpoint);
        Assert.assertEquals(discoveryAPIResponse.getUserinfoEndpoint(), newUserInfoEndpoint);
        Assert.assertEquals(discoveryAPIResponse.getJwksUri(), newJwksUri);
    }
}