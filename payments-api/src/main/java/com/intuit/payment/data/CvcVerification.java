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

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author dderose
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CvcVerification extends Entity {

	private static final long serialVersionUID = 1L;

	private String result = null;
	private Date date = null;

	public CvcVerification() {
	}

	private CvcVerification(Builder builder) {
		this.result = builder.result;
		this.date = builder.date;
	}

	/**
	 * @return
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for CvcVerification
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String result = null;
		private Date date = null;

		public Builder() {
		}

		public Builder result(String result) {
			this.result = result;
			return this;
		}

		public Builder date(Date date) {
			this.date = date;
			return this;
		}

		public CvcVerification build() {
			return new CvcVerification(this);
		}

	}
}

