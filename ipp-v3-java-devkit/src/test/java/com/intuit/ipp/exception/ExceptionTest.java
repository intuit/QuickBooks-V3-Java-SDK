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
package com.intuit.ipp.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExceptionTest extends ExceptionTestBase {

	@Test
	public void authenticationExceptionTest() {
		try {
			exceptionTest(AuthenticationException.class, true);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void authorizationExceptionExceptionTest() {
		try {
			exceptionTest(AuthorizationException.class, true);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void badRequestExceptionTest() {
		try {
			exceptionTest(BadRequestException.class, true);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void compressionExceptionTest() {
		try {
			exceptionTest(CompressionException.class, false);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void configurationExceptionTest() {
		try {
			exceptionTest(ConfigurationException.class, false);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void InternalServiceExceptionTest() {
		try {
			exceptionTest(InternalServiceException.class, true);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void invalidRequestExceptionTest() {
		try {
			exceptionTest(InvalidRequestException.class, true);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void invalidTokenExceptionTest() {
		try {
			exceptionTest(InvalidTokenException.class, true);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void serializationExceptionTest() {
		try {
			exceptionTest(SerializationException.class, false);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void serviceExceptionTest() {
		try {
			exceptionTest(ServiceException.class, true);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void serviceUnavailableExceptionTest() {
		try {
			exceptionTest(ServiceUnavailableException.class, true);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}

	@Test
	public void validationExceptionTest() {
		try {
			exceptionTest(ValidationException.class, true);
		} catch (Exception ex) {
			Assert.assertTrue(false, "Exception test failed : " + ex.getMessage());
		}
	}
}
