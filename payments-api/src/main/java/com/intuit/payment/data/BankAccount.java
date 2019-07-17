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
public class BankAccount extends Entity {

	private static final long serialVersionUID = 1L;

	private String id = null;
	private String name = null;
	private Date created = null;
	private Date updated = null;
	private BankAccountInputTypeEnum inputType = null;

	public enum BankAccountInputTypeEnum {
		KEYED
	};

	private String routingNumber = null;
	private String accountNumber = null;
	private AccountType accountType = null;

	public enum AccountType {
		PERSONAL_CHECKING, PERSONAL_SAVINGS
	};

	private String phone = null;

	@JsonProperty("default")
	private Boolean defaultValue = null;

	private String country = null;
	private String bankCode = null;
	
	private String entityId = null;
	private String entityType = null;
	private String entityVersion = null;

	public BankAccount() {
	}

	private BankAccount(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.created = builder.created;
		this.updated = builder.updated;
		this.inputType = builder.inputType;
		this.routingNumber = builder.routingNumber;
		this.accountNumber = builder.accountNumber;
		this.accountType = builder.accountType;
		this.phone = builder.phone;
		this.defaultValue = builder.defaultValue;
		this.country = builder.country;
		this.bankCode = builder.bankCode;
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
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getRoutingNumber() {
		return routingNumber;
	}

	/**
	 * @param routingNumber
	 */
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	/**
	 * @return
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return
	 */
	public BankAccountInputTypeEnum getInputType() {
		return inputType;
	}

	/**
	 * @param inputType
	 */
	public void setInputType(BankAccountInputTypeEnum inputType) {
		this.inputType = inputType;
	}

	/**
	 * @return
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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
	 * Builder class for BankAccount
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String id = null;
		private String name = null;
		private Date created = null;
		private Date updated = null;
		private BankAccountInputTypeEnum inputType = null;
		private String routingNumber = null;
		private String accountNumber = null;
		private AccountType accountType = null;
		private String phone = null;
		private Boolean defaultValue = null;
		private String country = null;
		private String bankCode = null;
		private String entityId = null;
		private String entityType = null;
		private String entityVersion = null;

		public Builder() {
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder created(Date created) {
			this.created = created;
			return this;
		}

		public Builder updated(Date updated) {
			this.updated = updated;
			return this;
		}

		public Builder inputType(BankAccountInputTypeEnum inputType) {
			this.inputType = inputType;
			return this;
		}

		public Builder routingNumber(String routingNumber) {
			this.routingNumber = routingNumber;
			return this;
		}

		public Builder accountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
			return this;
		}

		public Builder accountType(AccountType accountType) {
			this.accountType = accountType;
			return this;
		}

		public Builder phone(String phone) {
			this.phone = phone;
			return this;
		}

		public Builder defaultValue(Boolean defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		public Builder country(String country) {
			this.country = country;
			return this;
		}

		public Builder bankCode(String bankCode) {
			this.bankCode = bankCode;
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

		public BankAccount build() {
			return new BankAccount(this);
		}

	}

}

