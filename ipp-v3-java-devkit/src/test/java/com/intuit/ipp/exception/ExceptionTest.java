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
