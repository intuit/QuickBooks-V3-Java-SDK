JAVA-PAYMENTS-SDK
==================

**Help:** [Support](https://developer.intuit.com/help), [Samples](https://developer.intuit.com/docs/0100_quickbooks_online/0400_tools/0005_sdks/0200_java/0004_sample_code_and_sample_apps) <br/>
**Documentation:** [User Guide](https://developer.intuit.com/app/developer/qbpayments/docs/develop/using-an-sdk-to-integrate-with-the-payments-api#java) <br/>
**Continuous Integration:** [![Build Status](https://travis-ci.org/intuit/QuickBooks-V3-Java-SDK.svg?branch=develop)](https://travis-ci.org/intuit/QuickBooks-V3-Java-SDK) <br/>
**Maven:** [![Data](https://maven-badges.herokuapp.com/maven-central/com.intuit.quickbooks-online/payments-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.intuit.quickbooks-online/payments-api) <br/>
**License:** [![Apache 2](https://img.shields.io/badge/license-Apache%202-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0) <br/>


## Overview
This Payments SDK provides a Java library that make it easier to call QuickBooks Online Payments API. This library supports the following APIs:

* Token creation for card and bankAccount.
* BankAccount functions like create, delete, retrieve.
* Card functions like create, delete, retrieve.
* Charge functions like create, capture, retrieve, refund.
* ECheck functions like create retrieve, refund.

In addition to the above, the library also supports, logging, error handling, serialization/deserialization and capturing intuit_tid for easier debugging.
Note: This library works only for OAuth2 apps.

## System Requirements
The SDK works on JDK 1.6 and above.

## Install
The SDK can be installed using one of the ways below-
1. Download the latest version of the jar from [maven](https://search.maven.org/search?q=quickbooks) and add it to your projects classpath
	```sh
    payment-api.jar
    ```

2. Add the following dependency to the build.gradle file. Make sure to use the latest version of the SDK found [here](https://search.maven.org/search?q=quickbooks)
    ```sh
    compile("com.intuit.quickbooks-online:payments-api:5.0.0")
     ```

3. Add the following dependency to the pom.xml file. Make sure to use the latest version of the SDK found [here](https://search.maven.org/search?q=quickbooks)
	```sh
	 <dependency>
	    <groupId>com.intuit.quickbooks-online</groupId>
	    <artifactId>payments-api</artifactId>
	    <version>5.0.0</version>
	</dependency>
	 ```

## Usage
1. Create the RequestContext object as shown below. Pass in the accessToken and Environment. 
```java
RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
```
The above code automatically sets a unique requestid as well. To provide your custom requestid use the below code-
```java
RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX)
				.requestId(requestId).build();
```
You can also set Proxy parameters in the same way.

2. Create the Service object. The sample below shows how to create a TokenService, you can create other service objects - Echeck, Charge, Card, BankAccount in the same way.
```java
TokenService tokenService = new TokenService(requestContext);
```

3. Prepare Token request create token for a credit card
```java
Address address = new Address.Builder().region("CA").postalCode("94086")
		.streetAddress("1130 Kifer Rd").city("Sunnyvale")
		.country("US").build();
```

```java
Card card = new Card.Builder().expYear("2020").expMonth("02").address(address)
		.name("emulate=0").cvc("123")
		.number("4111111111111111").build();
```

```java
Token tokenRequest = new Token.Builder().card(card).build();
```

4. Call the TokenService to create token
```java
Token token = tokenService.createToken(tokenRequest);
```

5. Retrieve token value
```java
token.getValue();
```

## Logging important attributes
Makes sure to log intuit_tid and requestId for each of your request for easier debugging

For logging intuit_tid, use the following method
```java
token.getIntuit_tid();
```

For logging requestId, use the following method
```java
token.getRequestId();
```

## Sample
For more samples on how to create other entities and operation, take a look at the sample application below :  
[sample](https://github.com/IntuitDeveloper/SampleApp-Payments-Java/tree/master/src/main/java/com/intuit/sample/paymentsdk)


