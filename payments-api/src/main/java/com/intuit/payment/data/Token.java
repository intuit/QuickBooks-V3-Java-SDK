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
public class Token extends Entity {

	private static final long serialVersionUID = 1L;

	private Card card = null;
	private BankAccount bankAccount = null;
	private String value = null;

	public Token() {
	}

	private Token(Builder builder) {
		this.card = builder.card;
		this.bankAccount = builder.bankAccount;
		this.value = builder.value;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @param card
	 */
	public void setCard(Card card) {
		this.card = card;
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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for Token
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private Card card = null;
		private BankAccount bankAccount = null;
		private String value = null;

		public Builder() {
		}

		public Builder card(Card card) {
			this.card = card;
			return this;
		}

		public Builder bankAccount(BankAccount bankAccount) {
			this.bankAccount = bankAccount;
			return this;
		}

		public Builder value(String value) {
			this.value = value;
			return this;
		}

		public Token build() {
			return new Token(this);
		}

	}

}

