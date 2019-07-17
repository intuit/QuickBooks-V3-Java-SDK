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

/**
 * @author dderose
 *
 */
public class QueryResponse extends Entity {

	private static final long serialVersionUID = 1L;

	private List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
	private List<Card> cards = new ArrayList<Card>();

	public QueryResponse() {
	}

	private QueryResponse(Builder builder) {
		this.bankAccounts = builder.bankAccounts;
		this.cards = builder.cards;
	}

	/**
	 * @return
	 */
	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	/**
	 * @param bankAccounts
	 */
	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	/**
	 * @return
	 */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * @param cards
	 */
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for QueryResponse
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		private List<Card> cards = new ArrayList<Card>();

		public Builder() {
		}

		public Builder bankAccounts(List<BankAccount> bankAccounts) {
			this.bankAccounts = bankAccounts;
			return this;
		}

		public Builder cards(List<Card> cards) {
			this.cards = cards;
			return this;
		}

		public QueryResponse build() {
			return new QueryResponse(this);
		}

	}
}

