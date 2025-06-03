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
public class Charge extends Entity {

	private static final long serialVersionUID = 1L;

	private String id = null;
	private Date created = null;
	private ChargeStatus status = null;

	public enum ChargeStatus {
		AUTHORIZED, DECLINED, CAPTURED, CANCELLED, REFUNDED, SETTLED,PENDING,
		DISPUTED, RETURNED, WITHHELD
	};

	private BigDecimal amount = null;
	private String currency = null;
	private String token = null;
	private Card card = null;
	private PaymentContext context = null;
	private Boolean capture = null;
	private String authCode = null;
	private Capture captureDetail = null;
	private Refund[] refundDetail = null;
	private String description = null;
	private String avsStreet = null;
	private String avsZip = null;
	private String cardSecurityCodeMatch = null;
	private String appType = null;
	private String cardOnFile = null;

	public Charge() {
	}

	private Charge(Builder builder) {
		this.id = builder.id;
		this.created = builder.created;
		this.status = builder.status;
		this.amount = builder.amount;
		this.currency = builder.currency;
		this.token = builder.token;
		this.card = builder.card;
		this.context = builder.context;
		this.capture = builder.capture;
		this.authCode = builder.authCode;
		this.captureDetail = builder.captureDetail;
		this.refundDetail = builder.refundDetail;
		this.description = builder.description;
		this.avsStreet = builder.avsStreet;
		this.avsZip = builder.avsZip;
		this.cardSecurityCodeMatch = builder.cardSecurityCodeMatch;
		this.appType = builder.appType;
		this.cardOnFile = builder.cardOnFile;
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
	 * Status of the transaction
	 *
	 * @return Status of the transaction
	 */
	public ChargeStatus getStatus() {
		return status;
	}

	/**
	 * Status of the transaction
	 *
	 * @param status
	 *            Status of the transaction
	 */
	public void setStatus(ChargeStatus status) {
		this.status = status;
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
	 * Three-letter ISO 4217 currency code representing the currency in which
	 * the charge was made
	 *
	 * @return Three-letter ISO 4217 currency code representing the currency in
	 *         which the charge was made
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Three-letter ISO 4217 currency code representing the currency in which
	 * the charge was made
	 *
	 * @param currency
	 *            Three-letter ISO 4217 currency code representing the currency
	 *            in which the charge was made
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
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
	 * Credit card details associated with this charge
	 *
	 * @return Credit card details associated with this charge
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * Credit card details associated with this charge
	 *
	 * @param card
	 *            Credit card details associated with this charge
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * Optional additional data that will be stored with this charge
	 *
	 * @return Optional additional data that will be stored with this charge
	 */
	public PaymentContext getContext() {
		return context;
	}

	/**
	 * Optional additional data that will be stored with this charge
	 *
	 * @param context
	 *            Optional additional data that will be stored with this charge
	 */
	public void setContext(PaymentContext context) {
		this.context = context;
	}

	/**
	 * Capture flag
	 *
	 * @return Capture flag
	 */
	public Boolean getCapture() {
		return capture;
	}

	/**
	 * Capture flag
	 *
	 * @param capture
	 *            Capture flag
	 */
	public void setCapture(Boolean capture) {
		this.capture = capture;
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
	 * Charge capture detail. Available for charges previously authorized
	 *
	 * @return Charge capture detail. Available for charges previously
	 *         authorized
	 */
	public Capture getCaptureDetail() {
		return captureDetail;
	}

	/**
	 * Charge capture detail. Available for charges previously authorized
	 *
	 * @param captureDetail
	 *            Charge capture detail. Available for charges previously
	 *            authorized
	 */
	public void setCaptureDetail(Capture captureDetail) {
		this.captureDetail = captureDetail;
	}

	/**
	 * Details for one or more refunds against this charge
	 *
	 * @return Details for one or more refunds against this charge
	 */
	public Refund[] getRefundDetail() {
		return refundDetail;
	}

	/**
	 * Details for one or more refunds against this charge
	 *
	 * @param refundDetail
	 *            Details for one or more refunds against this charge
	 */
	public void setRefundDetail(Refund[] refundDetail) {
		this.refundDetail = refundDetail;
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
	public String getAvsStreet() {
		return avsStreet;
	}

	/**
	 * @param avsStreet
	 */
	public void setAvsStreet(String avsStreet) {
		this.avsStreet = avsStreet;
	}

	/**
	 * @return
	 */
	public String getAvsZip() {
		return avsZip;
	}

	/**
	 * @param avsZip
	 */
	public void setAvsZip(String avsZip) {
		this.avsZip = avsZip;
	}

	/**
	 * @return
	 */
	public String getCardSecurityCodeMatch() {
		return cardSecurityCodeMatch;
	}

	/**
	 * @param cardSecurityCodeMatch
	 */
	public void setCardSecurityCodeMatch(String cardSecurityCodeMatch) {
		this.cardSecurityCodeMatch = cardSecurityCodeMatch;
	}

	/**
	 * @return
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * @param appType
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * @return
	 */
	public String getCardOnFile() {
		return cardOnFile;
	}

	/**
	 * @param cardOnFile
	 */
	public void setCardOnFile(String cardOnFile) {
		this.cardOnFile = cardOnFile;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for Charge
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String id = null;
		private Date created = null;
		private ChargeStatus status = null;
		private BigDecimal amount = null;
		private String currency = null;
		private String token = null;
		private Card card = null;
		private PaymentContext context = null;
		private Boolean capture = null;
		private String authCode = null;
		private Capture captureDetail = null;
		private Refund[] refundDetail = null;
		private String description = null;
		private String avsStreet = null;
		private String avsZip = null;
		private String cardSecurityCodeMatch = null;
		private String appType = null;
		private String cardOnFile = null;

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

		public Builder status(ChargeStatus status) {
			this.status = status;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public Builder currency(String currency) {
			this.currency = currency;
			return this;
		}

		public Builder token(String token) {
			this.token = token;
			return this;
		}

		public Builder card(Card card) {
			this.card = card;
			return this;
		}

		public Builder context(PaymentContext context) {
			this.context = context;
			return this;
		}

		public Builder capture(Boolean capture) {
			this.capture = capture;
			return this;
		}

		public Builder authCode(String authCode) {
			this.authCode = authCode;
			return this;
		}

		public Builder captureDetail(Capture captureDetail) {
			this.captureDetail = captureDetail;
			return this;
		}

		public Builder refundDetail(Refund[] refundDetail) {
			this.refundDetail = refundDetail;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder avsStreet(String avsStreet) {
			this.avsStreet = avsStreet;
			return this;
		}

		public Builder avsZip(String avsZip) {
			this.avsZip = avsZip;
			return this;
		}

		public Builder cardSecurityCodeMatch(String cardSecurityCodeMatch) {
			this.cardSecurityCodeMatch = cardSecurityCodeMatch;
			return this;
		}

		public Builder appType(String appType) {
			this.appType = appType;
			return this;
		}

		public Builder cardOnFile(String cardOnFile) {
			this.cardOnFile = cardOnFile;
			return this;
		}

		public Charge build() {
			return new Charge(this);
		}

	}

}

