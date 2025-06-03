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
public class CardPresent extends Entity {

	private static final long serialVersionUID = 1L;

	private String track1 = null;
	private String track2 = null;
	private String ksn = null;
	private String pinBlock = null;

	public CardPresent() {
		
	}

	private CardPresent(Builder builder) {
		this.track1 = builder.track1;
		this.track2 = builder.track2;
		this.ksn = builder.ksn;
		this.pinBlock = builder.pinBlock;
	}

	public String getTrack1() {
		return track1;
	}

	public void setTrack1(String track1) {
		this.track1 = track1;
	}

	public String getTrack2() {
		return track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
	}

	public String getKsn() {
		return ksn;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}

	public String getPinBlock() {
		return pinBlock;
	}

	public void setPinBlock(String pinBlock) {
		this.pinBlock = pinBlock;
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

		private String track1 = null;
		private String track2 = null;
		private String ksn = null;
		private String pinBlock = null;

		public Builder() {
		}

		public Builder track1(String track1) {
			this.track1 = track1;
			return this;
		}

		public Builder track2(String track2) {
			this.track2 = track2;
			return this;
		}

		public Builder ksn(String ksn) {
			this.ksn = ksn;
			return this;
		}

		public Builder pinBlock(String pinBlock) {
			this.pinBlock = pinBlock;
			return this;
		}
		
		public CardPresent build() {
			return new CardPresent(this);
		}


	}

}

