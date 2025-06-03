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

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author dderose
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error extends Entity {

	private static final long serialVersionUID = 1L;

	private String code = null;
	private String type = null;
	private String message = null;
	private String detail = null;
	private String moreInfo = null;
	private String infoLink = null;

	public Error() {
	}

	private Error(Builder builder) {
		this.code = builder.code;
		this.type = builder.type;
		this.message = builder.message;
		this.detail = builder.detail;
		this.moreInfo = builder.moreInfo;
		this.infoLink = builder.infoLink;
	}

	/**
	 * Error Code
	 *
	 * @return Error Code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Error Code
	 *
	 * @param code
	 *            Error Code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Type for the error
	 *
	 * @return Type for the error
	 */
	public String getType() {
		return type;
	}

	/**
	 * Type for the error
	 *
	 * @param type
	 *            Type for the error
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Reason for the error
	 *
	 * @return Reason for the error
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Reason for the error
	 *
	 * @param message
	 *            Reason for the error
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Additonal detail of the error
	 *
	 * @return Additonal detail of the error
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * Additonal detail of the error
	 *
	 * @param detail
	 *            Additonal detail of the error
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * More info
	 *
	 * @return More info
	 */
	public String getMoreInfo() {
		return moreInfo;
	}

	/**
	 * More info
	 *
	 * @param moreInfo
	 *            More info
	 */
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	/**
	 * Info link
	 *
	 * @return Info link
	 */
	public String getInfoLink() {
		return infoLink;
	}

	/**
	 * Info link
	 *
	 * @param infoLink
	 *            Info link
	 */
	public void setInfoLink(String infoLink) {
		this.infoLink = infoLink;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for Error
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String code = null;
		private String type = null;
		private String message = null;
		private String detail = null;
		private String moreInfo = null;
		private String infoLink = null;

		public Builder() {
		}

		public Builder code(String code) {
			this.code = code;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder detail(String detail) {
			this.detail = detail;
			return this;
		}

		public Builder moreInfo(String moreInfo) {
			this.moreInfo = moreInfo;
			return this;
		}

		public Builder infoLink(String infoLink) {
			this.infoLink = infoLink;
			return this;
		}

		public Error build() {
			return new Error(this);
		}

	}
}

