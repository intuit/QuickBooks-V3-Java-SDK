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
package com.intuit.payment.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dderose
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Errors extends Entity {

	private static final long serialVersionUID = 1L;

	@JsonProperty("errors")
	private List<Error> errorList = new ArrayList<Error>();

	public Errors() {
	}

	private Errors(Builder builder) {
		this.errorList = builder.errorList;
	}

	/**
	 * @return
	 */
	public List<Error> getErrorList() {
		return errorList;
	}

	/**
	 * @param errorList
	 */
	public void setErrorList(List<Error> errorList) {
		this.errorList = errorList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for Errors
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private List<Error> errorList = new ArrayList<Error>();

		public Builder(List<Error> errorList) {
			this.errorList = errorList;
		}

		public Errors build() {
			return new Errors(this);
		}

	}
}

