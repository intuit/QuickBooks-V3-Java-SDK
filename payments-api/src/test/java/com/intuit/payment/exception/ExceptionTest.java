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
package com.intuit.payment.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.payment.data.Error;
import com.intuit.payment.data.Errors;


/**
 * @author dderose
 *
 */
public class ExceptionTest {

	@Test
	public void baseExceptionTest() {
		try {
			exceptionTest(BaseException.class, true);
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
	
	public <T extends BaseException> void exceptionTest(Class<T> exceptionType, boolean isListSupported)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {

		String errorMessage = "Test error message " + exceptionType.getName();
		String errorDetail = "Test error detail " + exceptionType.getName();
		String errorCode = "Test error Code " + exceptionType.getName();
		List<Error> errorList = null;
		Class<?> classTemplate = exceptionType;
		BaseException exceptionObject = null;

		// Testing the constructor with only message parameter
		Constructor<?> constructor = classTemplate.getConstructor(new Class[] { String.class });
		exceptionObject = (BaseException) constructor.newInstance(errorMessage);
		try {
			throw exceptionObject;
		} catch (BaseException ex) {
			Assert.assertEquals(ex.getMessage(), errorMessage, "Exception message is not proper");
		}

		// Testing the constructor with throwable parameter
		String innerExceptionMessage = "Test InnerException";
		Exception innerException = new Exception(innerExceptionMessage);
		constructor = classTemplate.getConstructor(new Class[] { Throwable.class });
		exceptionObject = (BaseException) constructor.newInstance(innerException);
		try {
			throw exceptionObject;
		} catch (BaseException ex) {
			Assert.assertEquals(innerExceptionMessage, ex.getThrowable().getMessage(),
					"The inner exception is not set properly");
		}

		// Testing the constructor with throwable and message parameters
		constructor = classTemplate.getConstructor(new Class[] { String.class, Throwable.class });
		exceptionObject = (BaseException) constructor.newInstance(errorMessage, innerException);
		try {
			throw exceptionObject;
		} catch (BaseException ex) {
			Assert.assertEquals(innerExceptionMessage, ex.getThrowable().getMessage(),
					"The inner exception is not set properly");
			Assert.assertEquals(ex.getMessage(), errorMessage, "Exception message is not proper");
		}

		// Testing the constructor with List<Error> parameter
		if (isListSupported) {
			errorList = getDummyErrorList(errorCode, errorDetail, errorMessage);
			Errors errors = new Errors.Builder(errorList).build();
			constructor = classTemplate.getConstructor(new Class[] { Errors.class });
			exceptionObject = (BaseException) constructor.newInstance(errors);
			try {
				throw exceptionObject;
			} catch (BaseException ex) {
				boolean isListFormatProper = checkMessageFormatForList(errorCode, errorDetail, errorMessage, ex.getMessage());
				Assert.assertTrue(isListFormatProper, "Exception message is not proper");
			}
		}
	}
	
	private List<Error> getDummyErrorList(String errorCode, String errorDetail, String errorMessage) {
		ArrayList<Error> errorList = new ArrayList<Error>();
		Error error = new Error();

		error.setCode(errorCode);
		error.setDetail(errorDetail);
		error.setMessage(errorMessage);

		errorList.add(error);
		return errorList;

	}

	private boolean checkMessageFormatForList(String errorCode, String errorDetail, String errorMessage, String actualMessageRecieved) {

		String message = "ERROR CODE:" + errorCode + ", ERROR MESSAGE:" + errorMessage + ", ERROR DETAIL:" + errorDetail + ", ERROR INFO LINK:null, ERROR MORE INFO:null, ERROR TYPE:null";
		actualMessageRecieved = actualMessageRecieved.trim();
		if (message.equals(actualMessageRecieved)) {
			return true;
		} else {
			return false;
		}

	}


}
