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

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author dderose
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentContext extends Entity {

	private static final long serialVersionUID = 1L;

	private BigDecimal tax = null;
	private DeviceInfo deviceInfo = null;
	private Boolean recurring = null;
	private String mobile = null;
	private String isEcommerce = null;

	public PaymentContext() {
	}

	private PaymentContext(Builder builder) {
		this.tax = builder.tax;
		this.deviceInfo = builder.deviceInfo;
		this.recurring = builder.recurring;
		this.mobile = builder.mobile;
		this.isEcommerce = builder.isEcommerce;
	}

	/**
	 * Sales Tax - required for commercial card processing
	 *
	 * @return Sales Tax - required for commercial card processing
	 */
	public BigDecimal getTax() {
		return tax;
	}

	/**
	 * Sales Tax - required for commercial card processing
	 *
	 * @param tax
	 *            Sales Tax - required for commercial card processing
	 */
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	/**
	 * @return
	 */
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * @param deviceInfo
	 */
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	/**
	 * This boolean value should be set to true if the charge is recurring. This
	 * value is not applicable for capture or refund request and it won't be
	 * used.
	 *
	 * @return This boolean value should be set to true if the charge is
	 *         recurring. This value is not applicable for capture or refund
	 *         request and it won't be used.
	 */
	public Boolean getRecurring() {
		return recurring;
	}

	/**
	 * This boolean value should be set to true if the charge is recurring. This
	 * value is not applicable for capture or refund request and it won't be
	 * used.
	 *
	 * @param recurring
	 *            This boolean value should be set to true if the charge is
	 *            recurring. This value is not applicable for capture or refund
	 *            request and it won't be used.
	 */
	public void setRecurring(Boolean recurring) {
		this.recurring = recurring;
	}

	/**
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return
	 */
	public String getIsEcommerce() {
		return isEcommerce;
	}

	/**
	 * @param isEcommerce
	 */
	public void setIsEcommerce(String isEcommerce) {
		this.isEcommerce = isEcommerce;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for PaymentContext
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private BigDecimal tax = null;
		private DeviceInfo deviceInfo = null;
		private Boolean recurring = null;
		private String mobile = null;
		private String isEcommerce = null;

		public Builder() {
		}

		public Builder tax(BigDecimal tax) {
			this.tax = tax;
			return this;
		}

		public Builder deviceInfo(DeviceInfo deviceInfo) {
			this.deviceInfo = deviceInfo;
			return this;
		}

		public Builder recurring(Boolean recurring) {
			this.recurring = recurring;
			return this;
		}

		public Builder mobile(String mobile) {
			this.mobile = mobile;
			return this;
		}

		public Builder isEcommerce(String isEcommerce) {
			this.isEcommerce = isEcommerce;
			return this;
		}

		public PaymentContext build() {
			return new PaymentContext(this);
		}

	}

}

