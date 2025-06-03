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
public class Refund extends Entity {

	private static final long serialVersionUID = 1L;

	private String id = null;
	private Date created = null;
	private RefundStatus status = null;

	public enum RefundStatus {
		ISSUED, DECLINED, SETTLED, VOIDED, SUCCEEDED
	};

	private BigDecimal amount = null;
	private PaymentContext context = null;
	private String description = null;
	private String type = null;

	public Refund() {
	}

	private Refund(Builder builder) {
		this.id = builder.id;
		this.created = builder.created;
		this.status = builder.status;
		this.amount = builder.amount;
		this.context = builder.context;
		this.description = builder.description;
		this.type = builder.type;
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
	 * Status of this refund
	 *
	 * @return Status of this refund
	 */
	public RefundStatus getStatus() {
		return status;
	}

	/**
	 * Status of this refund
	 *
	 * @param status
	 *            Status of this refund
	 */
	public void setStatus(RefundStatus status) {
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
	 * Optional additional data that will be stored with this refund
	 *
	 * @return Optional additional data that will be stored with this refund
	 */
	public PaymentContext getContext() {
		return context;
	}

	/**
	 * Optional additional data that will be stored with this refund
	 *
	 * @param context
	 *            Optional additional data that will be stored with this refund
	 */
	public void setContext(PaymentContext context) {
		this.context = context;
	}

	/**
	 * Optional description that will be stored with this refund
	 *
	 * @return Optional description that will be stored with this refund
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Optional description that will be stored with this refund
	 *
	 * @param description
	 *            Optional description that will be stored with this refund
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for Refund
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String id = null;
		private Date created = null;
		private RefundStatus status = null;
		private BigDecimal amount = null;
		private PaymentContext context = null;
		private String description = null;
		private String type = null;

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

		public Builder status(RefundStatus status) {
			this.status = status;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public Builder context(PaymentContext context) {
			this.context = context;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Refund build() {
			return new Refund(this);
		}

	}
}

