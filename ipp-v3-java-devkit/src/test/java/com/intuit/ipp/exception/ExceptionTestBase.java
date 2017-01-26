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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import com.intuit.ipp.data.Error;
import com.intuit.ipp.util.Logger;

public class ExceptionTestBase {
	private org.slf4j.Logger log = Logger.getLogger();

	private List<Error> getDummyErrorList(String errorCode, String errorDetail, String errorMessage, String errorElement) {
		ArrayList<Error> errorList = new ArrayList<Error>();
		Error error = new Error();

		error.setCode(errorCode);
		error.setDetail(errorDetail);
		error.setElement(errorElement);
		error.setMessage(errorMessage);

		errorList.add(error);
		return errorList;

	}

	private boolean checkMessageFormatForList(String errorCode, String errorDetail, String errorMessage, String errorElement, List<Error> errorList,
			String actualMessageRecieved) {

		String message = "ERROR CODE:" + errorCode + ", ERROR MESSAGE:" + errorMessage + ", ERROR DETAIL:" + errorDetail + ", MORE ERROR DETAIL:" + errorElement;
		actualMessageRecieved = actualMessageRecieved.trim();
		log.debug("ActualMessage is : " + actualMessageRecieved);
		log.debug("PreparedMessage is : " + message);
		if (message.equals(actualMessageRecieved))
			return true;
		else
			return false;

	}

	public <T extends FMSException> void exceptionTest(Class<T> exceptionType, boolean isListSupported) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {

		String errorMessage = "Test error message " + exceptionType.getName();
		String errorDetail = "Test error detail " + exceptionType.getName();
		String errorCode = "Test error Code " + exceptionType.getName();
		String errorElement = "Test error element " + exceptionType.getName();
		List<Error> errorList = null;
		Class<?> classTemplate = exceptionType;
		FMSException exceptionObject = null;

		//Testing the constructor with only message parameter
		Constructor<?> constructor = classTemplate.getConstructor(new Class[] { String.class });
		exceptionObject = (FMSException) constructor.newInstance(errorMessage);
		try {
			throw exceptionObject;
		} catch (FMSException ex) {
			Assert.assertEquals(ex.getMessage(), errorMessage, "Exception message is not proper");
		}

		//Testing the constructor with throwable parameter
		String innerExceptionMessage = "Test InnerException";
		Exception innerException = new Exception(innerExceptionMessage);
		constructor = classTemplate.getConstructor(new Class[] { Throwable.class });
		exceptionObject = (FMSException) constructor.newInstance(innerException);
		try {
			throw exceptionObject;
		} catch (FMSException ex) {
			Assert.assertEquals(innerExceptionMessage, ex.getThrowable().getMessage(), "The inner exception is not set properly");
		}

		//Testing the constructor with throwable and message parameters
		constructor = classTemplate.getConstructor(new Class[] { String.class, Throwable.class });
		exceptionObject = (FMSException) constructor.newInstance(errorMessage, innerException);
		try {
			throw exceptionObject;
		} catch (FMSException ex) {
			Assert.assertEquals(innerExceptionMessage, ex.getThrowable().getMessage(), "The inner exception is not set properly");
			Assert.assertEquals(ex.getMessage(), errorMessage, "Exception message is not proper");
		}

		//Testing the constructor with List<Error> parameter
		if (isListSupported) {
			errorList = getDummyErrorList(errorCode, errorDetail, errorMessage, errorElement);
			constructor = classTemplate.getConstructor(new Class[] { List.class });
			exceptionObject = (FMSException) constructor.newInstance(errorList);
			try {
				throw exceptionObject;
			} catch (FMSException ex) {
				boolean isListFormatProper = checkMessageFormatForList(errorCode, errorDetail, errorMessage, errorElement, errorList, ex.getMessage());
				Assert.assertTrue(isListFormatProper, "Exception message is not proper");
			}
		}
	}
}
