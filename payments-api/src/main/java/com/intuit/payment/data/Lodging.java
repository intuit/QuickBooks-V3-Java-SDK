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
public class Lodging extends Entity {

	private static final long serialVersionUID = 1L;
	
	private String folioID = null;
	private String chargeType = null;
	private Date checkInDate = null;
	private Date checkOutDate = null;
	private String lengthOfStay = null; 
	private BigDecimal roomRate = null;
	private String[] extraCharges = null;
	private String specialProgram = null;
	private BigDecimal totalAuthAmount = null;


	public Lodging() {
	}

	private Lodging(Builder builder) {
		this.folioID = builder.folioID;
		this.chargeType = builder.chargeType;
		this.checkInDate = builder.checkInDate;
		this.checkOutDate = builder.checkOutDate;
		this.lengthOfStay = builder.lengthOfStay;
		this.roomRate = builder.roomRate;
		this.extraCharges = builder.extraCharges;
		this.specialProgram = builder.specialProgram;
		this.totalAuthAmount = builder.totalAuthAmount;
	}

	public String getFolioID() {
		return folioID;
	}

	public void setFolioID(String folioID) {
		this.folioID = folioID;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getLengthOfStay() {
		return lengthOfStay;
	}

	public void setLengthOfStay(String lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}

	public BigDecimal getRoomRate() {
		return roomRate;
	}

	public void setRoomRate(BigDecimal roomRate) {
		this.roomRate = roomRate;
	}

	public String[] getExtraCharges() {
		return extraCharges;
	}

	public void setExtraCharges(String[] extraCharges) {
		this.extraCharges = extraCharges;
	}

	public String getSpecialProgram() {
		return specialProgram;
	}

	public void setSpecialProgram(String specialProgram) {
		this.specialProgram = specialProgram;
	}

	public BigDecimal getTotalAuthAmount() {
		return totalAuthAmount;
	}

	public void setTotalAuthAmount(BigDecimal totalAuthAmount) {
		this.totalAuthAmount = totalAuthAmount;
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

		private String folioID = null;
		private String chargeType = null;
		private Date checkInDate = null;
		private Date checkOutDate = null;
		private String lengthOfStay = null; 
		private BigDecimal roomRate = null;
		private String[] extraCharges = null;
		private String specialProgram = null;
		private BigDecimal totalAuthAmount = null;

		public Builder() {
		}

		public Builder folioID(String folioID) {
			this.folioID = folioID;
			return this;
		}

		public Builder chargeType(String chargeType) {
			this.chargeType = chargeType;
			return this;
		}

		public Builder checkInDate(Date checkInDate) {
			this.checkInDate = checkInDate;
			return this;
		}

		public Builder checkOutDate(Date checkOutDate) {
			this.checkOutDate = checkOutDate;
			return this;
		}

		public Builder lengthOfStay(String lengthOfStay) {
			this.lengthOfStay = lengthOfStay;
			return this;
		}
		
		public Builder roomRate(BigDecimal roomRate) {
			this.roomRate = roomRate;
			return this;
		}
		
		public Builder extraCharges(String[] extraCharges) {
			this.extraCharges = extraCharges;
			return this;
		}
		
		public Builder specialProgram(String specialProgram) {
			this.specialProgram = specialProgram;
			return this;
		}
		
		public Builder totalAuthAmount(BigDecimal totalAuthAmount) {
			this.totalAuthAmount = totalAuthAmount;
			return this;
		}

		public Lodging build() {
			return new Lodging(this);
		}

	}

}

