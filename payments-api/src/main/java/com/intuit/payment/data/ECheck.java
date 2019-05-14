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
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author dderose
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ECheck extends Entity {

	private static final long serialVersionUID = 1L;

	private String id = null;
	private Date created = null;
	private String authCode = null;
	private ECheckStatus status = null;

	public enum ECheckStatus {
		PENDING, SUCCEEDED, DECLINED, VOIDED, REFUNDED
	};

	private BigDecimal amount = null;
	private BankAccount bankAccount = null;
	private String token = null;
	private String bankAccountOnFile = null;
	private CheckContext context = null;
	private PaymentModeType paymentMode = null;

	public enum PaymentModeType {
		WEB
	};

	private String description = null;
	private String checkNumber = null;

	public ECheck() {
	}

	private ECheck(Builder builder) {
		this.id = builder.id;
		this.created = builder.created;
		this.authCode = builder.authCode;
		this.status = builder.status;
		this.amount = builder.amount;
		this.bankAccount = builder.bankAccount;
		this.token = builder.token;
		this.bankAccountOnFile = builder.bankAccountOnFile;
		this.context = builder.context;
		this.paymentMode = builder.paymentMode;
		this.description = builder.description;
		this.checkNumber = builder.checkNumber;
	}

	/**
	 * System generated alpha-numeric id
	 *
	 * @return System generated alpha-numeric id
	 */
	public String getId() {
		return id;
	}

	/**
	 * System generated alpha-numeric id
	 *
	 * @param id
	 *            System generated alpha-numeric id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Object create time, in ISO 8601 date-time format
	 *
	 * @return Object create time, in ISO 8601 date-time format
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Object create time, in ISO 8601 date-time format
	 *
	 * @param created
	 *            Object create time, in ISO 8601 date-time format
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * Amount of the transaction. Valid values for amount are in the range 0.00
	 * through 99999.99
	 *
	 * @return Amount of the transaction. Valid values for amount are in the
	 *         range 0.00 through 99999.99
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Amount of the transaction. Valid values for amount are in the range 0.00
	 * through 99999.99
	 *
	 * @param amount
	 *            Amount of the transaction. Valid values for amount are in the
	 *            range 0.00 through 99999.99
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Opaque representation of the credit card associated with this charge, as
	 * returned by the token endpoint
	 *
	 * @return Opaque representation of the credit card associated with this
	 *         charge, as returned by the token endpoint
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Opaque representation of the credit card associated with this charge, as
	 * returned by the token endpoint
	 *
	 * @param token
	 *            Opaque representation of the credit card associated with this
	 *            charge, as returned by the token endpoint
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Authorization code. Available for uncaptured charges, only
	 *
	 * @return Authorization code. Available for uncaptured charges, only
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * Authorization code. Available for uncaptured charges, only
	 *
	 * @param authCode
	 *            Authorization code. Available for uncaptured charges, only
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * Optional description that will be stored with this charge
	 *
	 * @return Optional description that will be stored with this charge
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Optional description that will be stored with this charge
	 *
	 * @param description
	 *            Optional description that will be stored with this charge
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public ECheckStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(ECheckStatus status) {
		this.status = status;
	}

	/**
	 * @return
	 */
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	/**
	 * @param bankAccount
	 */
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * @return
	 */
	public String getBankAccountOnFile() {
		return bankAccountOnFile;
	}

	/**
	 * @param bankAccountOnFile
	 */
	public void setBankAccountOnFile(String bankAccountOnFile) {
		this.bankAccountOnFile = bankAccountOnFile;
	}

	/**
	 * @return
	 */
	public CheckContext getContext() {
		return context;
	}

	/**
	 * @param context
	 */
	public void setContext(CheckContext context) {
		this.context = context;
	}

	/**
	 * @return
	 */
	public PaymentModeType getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode
	 */
	public void setPaymentMode(PaymentModeType paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return
	 */
	public String getCheckNumber() {
		return checkNumber;
	}

	/**
	 * @param checkNumber
	 */
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for ECheck
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String id = null;
		private Date created = null;
		private String authCode = null;
		private ECheckStatus status = null;
		private BigDecimal amount = null;
		private BankAccount bankAccount = null;
		private String token = null;
		private String bankAccountOnFile = null;
		private CheckContext context = null;
		private PaymentModeType paymentMode = null;
		private String description = null;
		private String checkNumber = null;

		public Builder() {
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder created(Date created) {
			this.created = created;
			return this;
		}

		public Builder authCode(String authCode) {
			this.authCode = authCode;
			return this;
		}

		public Builder status(ECheckStatus status) {
			this.status = status;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public Builder bankAccount(BankAccount bankAccount) {
			this.bankAccount = bankAccount;
			return this;
		}

		public Builder token(String token) {
			this.token = token;
			return this;
		}

		public Builder bankAccountOnFile(String bankAccountOnFile) {
			this.bankAccountOnFile = bankAccountOnFile;
			return this;
		}

		public Builder context(CheckContext context) {
			this.context = context;
			return this;
		}

		public Builder paymentMode(PaymentModeType paymentMode) {
			this.paymentMode = paymentMode;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder checkNumber(String checkNumber) {
			this.checkNumber = checkNumber;
			return this;
		}

		public ECheck build() {
			return new ECheck(this);
		}

	}

}

