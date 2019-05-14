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
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dderose
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card extends Entity {

	private static final long serialVersionUID = 1L;

	private String id = null;
	private String number = null;
	private String name = null;
	private Address address = null;
	private Date created = null;
	private Date updated = null;
	private String commercialCardCode = null;
	private CvcVerification cvcVerification = null;
	private String cardType = null;
	private String expMonth = null;
	private String expYear = null;
	private Boolean isBusiness = null;
	private Boolean isLevel3Eligible = null;

	@JsonProperty("default")
	private Boolean defaultValue = null;

	private String cvc = null;
	
	private String entityId = null;
	private String entityType = null;
	private String entityVersion = null;
	

	public Card() {
	}

	private Card(Builder builder) {
		this.id = builder.id;
		this.number = builder.number;
		this.name = builder.name;
		this.address = builder.address;
		this.created = builder.created;
		this.updated = builder.updated;
		this.commercialCardCode = builder.commercialCardCode;
		this.cvcVerification = builder.cvcVerification;
		this.cardType = builder.cardType;
		this.expMonth = builder.expMonth;
		this.expYear = builder.expYear;
		this.isBusiness = builder.isBusiness;
		this.isLevel3Eligible = builder.isLevel3Eligible;
		this.defaultValue = builder.defaultValue;
		this.cvc = builder.cvc;
		this.entityId = builder.entityId;
		this.entityType = builder.entityType;
		this.entityVersion = builder.entityVersion;
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
	 * Credit/debit card number
	 *
	 * @return Credit/debit card number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Credit/debit card number
	 *
	 * @param number
	 *            Credit/debit card number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Two digits indicating card's expiration month
	 *
	 * @return Two digits indicating card's expiration month
	 */
	public String getExpMonth() {
		return expMonth;
	}

	/**
	 * Two digits indicating card's expiration month
	 *
	 * @param expMonth
	 *            Two digits indicating card's expiration month
	 */
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	/**
	 * Four digits indicating card's expiration year
	 *
	 * @return Four digits indicating card's expiration year
	 */
	public String getExpYear() {
		return expYear;
	}

	/**
	 * Four digits indicating card's expiration year
	 *
	 * @param expYear
	 *            Four digits indicating card's expiration year
	 */
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	/**
	 * CVC code - Strongly recommended for screening fraudulent transactions
	 *
	 * @return CVC code - Strongly recommended for screening fraudulent
	 *         transactions
	 */
	public String getCvc() {
		return cvc;
	}

	/**
	 * CVC code - Strongly recommended for screening fraudulent transactions
	 *
	 * @param cvc
	 *            CVC code - Strongly recommended for screening fraudulent
	 *            transactions
	 */
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	/**
	 * Cardholder's name as it appears on the card
	 *
	 * @return Cardholder's name as it appears on the card
	 */
	public String getName() {
		return name;
	}

	/**
	 * Cardholder's name as it appears on the card
	 *
	 * @param name
	 *            Cardholder's name as it appears on the card
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Address
	 *
	 * @return Address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Address
	 *
	 * @param address
	 *            Address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Specific code that is applicable when the card used is a commercial card
	 * (corporate cards)
	 *
	 * @return Specific code that is applicable when the card used is a
	 *         commercial card (corporate cards)
	 */
	public String getCommercialCardCode() {
		return commercialCardCode;
	}

	/**
	 * Specific code that is applicable when the card used is a commercial card
	 * (corporate cards)
	 *
	 * @param commercialCardCode
	 *            Specific code that is applicable when the card used is a
	 *            commercial card (corporate cards)
	 */
	public void setCommercialCardCode(String commercialCardCode) {
		this.commercialCardCode = commercialCardCode;
	}

	/**
	 * @return
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return
	 */
	public CvcVerification getCvcVerification() {
		return cvcVerification;
	}

	/**
	 * @param cvcVerification
	 */
	public void setCvcVerification(CvcVerification cvcVerification) {
		this.cvcVerification = cvcVerification;
	}

	/**
	 * @return
	 */
	public Boolean getIsBusiness() {
		return isBusiness;
	}

	/**
	 * @param isBusiness
	 */
	public void setIsBusiness(Boolean isBusiness) {
		this.isBusiness = isBusiness;
	}

	/**
	 * @return
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return
	 */
	public Boolean getIsLevel3Eligible() {
		return isLevel3Eligible;
	}

	/**
	 * @param isLevel3Eligible
	 */
	public void setIsLevel3Eligible(Boolean isLevel3Eligible) {
		this.isLevel3Eligible = isLevel3Eligible;
	}

	/**
	 * @return
	 */
	public Boolean getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 */
	public void setDefaultValue(Boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityVersion() {
		return entityVersion;
	}

	public void setEntityVersion(String entityVersion) {
		this.entityVersion = entityVersion;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	/**
	 * Builder class for Card
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String id = null;
		private String number = null;
		private String name = null;
		private Address address = null;
		private Date created = null;
		private Date updated = null;
		private String commercialCardCode = null;
		private CvcVerification cvcVerification = null;
		private String cardType = null;
		private String expMonth = null;
		private String expYear = null;
		private Boolean isBusiness = null;
		private Boolean isLevel3Eligible = null;
		private Boolean defaultValue = null;
		private String cvc = null;
		private String entityId = null;
		private String entityType = null;
		private String entityVersion = null;

		public Builder() {
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder number(String number) {
			this.number = number;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder address(Address address) {
			this.address = address;
			return this;
		}

		public Builder createdDate(Date created) {
			this.created = created;
			return this;
		}

		public Builder updatedDate(Date updated) {
			this.updated = updated;
			return this;
		}

		public Builder commercialCardCode(String commercialCardCode) {
			this.commercialCardCode = commercialCardCode;
			return this;
		}

		public Builder cvcVerification(CvcVerification cvcVerification) {
			this.cvcVerification = cvcVerification;
			return this;
		}

		public Builder cardType(String cardType) {
			this.cardType = cardType;
			return this;
		}

		public Builder expMonth(String expMonth) {
			this.expMonth = expMonth;
			return this;
		}

		public Builder expYear(String expYear) {
			this.expYear = expYear;
			return this;
		}

		public Builder isBusiness(Boolean isBusiness) {
			this.isBusiness = isBusiness;
			return this;
		}

		public Builder isLevel3Eligible(Boolean isLevel3Eligible) {
			this.isLevel3Eligible = isLevel3Eligible;
			return this;
		}

		public Builder defaultValue(Boolean defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		public Builder cvc(String cvc) {
			this.cvc = cvc;
			return this;
		}
		
		public Builder entityId(String entityId) {
			this.entityId = entityId;
			return this;
		}
		
		public Builder entityType(String entityType) {
			this.entityType = entityType;
			return this;
		}
		
		public Builder entityVersion(String entityVersion) {
			this.entityVersion = entityVersion;
			return this;
		}

		public Card build() {
			return new Card(this);
		}

	}

}

