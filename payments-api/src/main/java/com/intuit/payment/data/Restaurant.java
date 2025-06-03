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
public class Restaurant extends Entity {

	private static final long serialVersionUID = 1L;

	private String serverID = null;
	private BigDecimal foodAmount = null;
	private BigDecimal beverageAmount = null;
	private BigDecimal taxAmount = null;
	private BigDecimal tipAmount = null;

	public Restaurant() {
	}

	private Restaurant(Builder builder) {
		this.serverID = builder.serverID;
		this.foodAmount = builder.foodAmount;
		this.beverageAmount = builder.beverageAmount;
		this.taxAmount = builder.taxAmount;
		this.tipAmount = builder.tipAmount;
	}

	public String getServerID() {
		return serverID;
	}

	public void setServerID(String serverID) {
		this.serverID = serverID;
	}

	public BigDecimal getFoodAmount() {
		return foodAmount;
	}

	public void setFoodAmount(BigDecimal foodAmount) {
		this.foodAmount = foodAmount;
	}

	public BigDecimal getBeverageAmount() {
		return beverageAmount;
	}

	public void setBeverageAmount(BigDecimal beverageAmount) {
		this.beverageAmount = beverageAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(BigDecimal tipAmount) {
		this.tipAmount = tipAmount;
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

		private String serverID = null;
		private BigDecimal foodAmount = null;
		private BigDecimal beverageAmount = null;
		private BigDecimal taxAmount = null;
		private BigDecimal tipAmount = null;

		public Builder() {
		}

		public Builder serverID(String serverID) {
			this.serverID = serverID;
			return this;
		}

		public Builder foodAmount(BigDecimal foodAmount) {
			this.foodAmount = foodAmount;
			return this;
		}

		public Builder beverageAmount(BigDecimal beverageAmount) {
			this.beverageAmount = beverageAmount;
			return this;
		}

		public Builder taxAmount(BigDecimal taxAmount) {
			this.taxAmount = taxAmount;
			return this;
		}

		public Builder tipAmount(BigDecimal tipAmount) {
			this.tipAmount = tipAmount;
			return this;
		}

		public Restaurant build() {
			return new Restaurant(this);
		}

	}

}

