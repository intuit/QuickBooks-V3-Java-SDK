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
package com.intuit.ipp.interceptors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.exception.AuthenticationException;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.exception.InvalidTokenException;

/**
 * Tests that response headers are captured into {@link ResponseElements} and surfaced on the
 * exceptions thrown by {@link HandleResponseInterceptor}.
 *
 * <p>Some failures are only distinguishable from a header. QuickBooks Online returns error code 3200
 * for both an expired and a revoked token, and the disambiguating fault detail is not always
 * populated, so the {@code WWW-Authenticate} challenge is what tells the two apart.
 *
 * <p>Self-contained: no credentials, no network.
 */
public class ResponseHeadersTest {

	private static final String WWW_AUTHENTICATE = "WWW-Authenticate";

	private static final String EXPIRED_CHALLENGE =
			"Bearer realm=\"Intuit\", error=\"invalid_token\", error_description=\"Token expired\"";

	private static final String REVOKED_CHALLENGE =
			"Bearer realm=\"Intuit\", error=\"invalid_token\", error_description=\"Token revoked\"";

	// ---------- ResponseElements normalisation ----------

	@Test
	public void testApacheHeadersAreLookedUpCaseInsensitively() {
		ResponseElements responseElements = new ResponseElements();
		responseElements.setResponseHeaders(new Header[] {
				new BasicHeader("www-authenticate", EXPIRED_CHALLENGE),
				new BasicHeader("Content-Type", "application/json")
		});

		Map<String, String> headers = responseElements.getResponseHeaders();
		// HTTP/2 lowercases header names, HTTP/1.1 usually title-cases them; both must resolve
		Assert.assertEquals(headers.get("WWW-Authenticate"), EXPIRED_CHALLENGE);
		Assert.assertEquals(headers.get("www-authenticate"), EXPIRED_CHALLENGE);
		Assert.assertEquals(headers.get("content-type"), "application/json");
	}

	@Test
	public void testRepeatedApacheHeaderKeepsLastValue() {
		ResponseElements responseElements = new ResponseElements();
		responseElements.setResponseHeaders(new Header[] {
				new BasicHeader("Strict-Transport-Security", "max-age=31536000"),
				new BasicHeader("Strict-Transport-Security", "max-age=15552000")
		});

		Assert.assertEquals(responseElements.getResponseHeaders().get("Strict-Transport-Security"),
				"max-age=15552000");
	}

	@Test
	public void testHeaderFieldsFromUrlConnection() {
		Map<String, List<String>> headerFields = new HashMap<String, List<String>>();
		headerFields.put("WWW-Authenticate", Arrays.asList(REVOKED_CHALLENGE));
		headerFields.put("Set-Cookie", Arrays.asList("a=1", "b=2"));
		// HttpURLConnection returns the status line keyed under null
		headerFields.put(null, Arrays.asList("HTTP/1.1 401 Unauthorized"));

		ResponseElements responseElements = new ResponseElements();
		responseElements.setResponseHeaderFields(headerFields);

		Map<String, String> headers = responseElements.getResponseHeaders();
		Assert.assertEquals(headers.get("www-authenticate"), REVOKED_CHALLENGE);
		Assert.assertEquals(headers.get("Set-Cookie"), "b=2");
		// the null-keyed status line entry is dropped, leaving only the two real headers
		Assert.assertEquals(headers.size(), 2);
	}

	@Test
	public void testHeadersDefaultToEmptyNotNull() {
		Assert.assertNotNull(new ResponseElements().getResponseHeaders());
		Assert.assertTrue(new ResponseElements().getResponseHeaders().isEmpty());
	}

	@Test
	public void testNullHeadersAreTolerated() {
		ResponseElements responseElements = new ResponseElements();
		responseElements.setResponseHeaders((Header[]) null);
		Assert.assertTrue(responseElements.getResponseHeaders().isEmpty());

		responseElements.setResponseHeaderFields(null);
		Assert.assertTrue(responseElements.getResponseHeaders().isEmpty());
	}

	// ---------- exception enrichment ----------

	@Test
	public void testFaultExceptionCarriesResponseHeaders() {
		IntuitMessage intuitMessage = faultMessage("Authentication", "3200");
		intuitMessage.getResponseElements().setResponseHeaders(new Header[] {
				new BasicHeader("www-authenticate", EXPIRED_CHALLENGE)
		});

		try {
			new HandleResponseInterceptor().execute(intuitMessage);
			Assert.fail("Expected AuthenticationException");
		} catch (FMSException e) {
			Assert.assertTrue(e instanceof AuthenticationException);
			Assert.assertEquals(e.getResponseHeader(WWW_AUTHENTICATE), EXPIRED_CHALLENGE);
			// the header is what distinguishes this from a revoked token - code 3200 does not
			Assert.assertEquals(e.getErrorList().get(0).getCode(), "3200");
		}
	}

	@Test
	public void testStatusCodeFallbackExceptionCarriesResponseHeaders() {
		// No parseable IntuitResponse, so HandleResponseInterceptor falls back to the status code
		IntuitMessage intuitMessage = new IntuitMessage();
		ResponseElements responseElements = intuitMessage.getResponseElements();
		responseElements.setStatusLine(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 401, "Unauthorized"));
		responseElements.setStatusCode(401);
		responseElements.setResponseHeaders(new Header[] {
				new BasicHeader("WWW-Authenticate", REVOKED_CHALLENGE)
		});

		try {
			new HandleResponseInterceptor().execute(intuitMessage);
			Assert.fail("Expected InvalidTokenException");
		} catch (FMSException e) {
			Assert.assertTrue(e instanceof InvalidTokenException);
			Assert.assertEquals(e.getResponseHeader("www-authenticate"), REVOKED_CHALLENGE);
		}
	}

	@Test
	public void testExceptionWithoutHeadersReturnsEmptyMap() {
		FMSException e = new FMSException("no headers captured");
		Assert.assertNotNull(e.getResponseHeaders());
		Assert.assertTrue(e.getResponseHeaders().isEmpty());
		Assert.assertNull(e.getResponseHeader(WWW_AUTHENTICATE));
		Assert.assertNull(e.getResponseHeader(null));
	}

	@Test
	public void testLookupsAreNullSafeAndMissesReturnNull() {
		IntuitMessage intuitMessage = faultMessage("Authentication", "3200");
		intuitMessage.getResponseElements().setResponseHeaders(new Header[] {
				new BasicHeader("WWW-Authenticate", EXPIRED_CHALLENGE)
		});

		try {
			new HandleResponseInterceptor().execute(intuitMessage);
			Assert.fail("Expected AuthenticationException");
		} catch (FMSException e) {
			// the map is ordered by a case-insensitive comparator, so a raw get(null) would throw;
			// the accessor must absorb that for callers
			Assert.assertNull(e.getResponseHeader(null));
			Assert.assertNull(e.getResponseHeader("X-Not-Present"));
		}
	}

	// ---------- helpers ----------

	private IntuitMessage faultMessage(String faultType, String errorCode) {
		Error error = new Error();
		error.setCode(errorCode);
		error.setMessage("message=AuthenticationFailed; errorCode=00" + errorCode + "; statusCode=401");

		List<Error> errors = new ArrayList<Error>();
		errors.add(error);

		Fault fault = new Fault();
		fault.setType(faultType);
		fault.setError(errors);

		IntuitResponse intuitResponse = new IntuitResponse();
		intuitResponse.setFault(fault);

		IntuitMessage intuitMessage = new IntuitMessage();
		intuitMessage.getResponseElements().setResponse(intuitResponse);
		return intuitMessage;
	}
}
