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
public class Address extends Entity {

	private static final long serialVersionUID = 1L;
	private String streetAddress = null;
	private String city = null;
	private String region = null;
	private String country = null;
	private String postalCode = null;

	public Address() {
	}

	private Address(Builder builder) {
		this.streetAddress = builder.streetAddress;
		this.city = builder.city;
		this.region = builder.region;
		this.country = builder.country;
		this.postalCode = builder.postalCode;
	}

	/**
	 * Full street adddress, which may include house number and street name (CR
	 * acceptable)
	 *
	 * @return Full street adddress, which may include house number and street
	 *         name (CR acceptable)
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * Full street adddress, which may include house number and street name (CR
	 * acceptable)
	 *
	 * @param streetAddress
	 *            Full street adddress, which may include house number and
	 *            street name (CR acceptable)
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * City
	 *
	 * @return City
	 */
	public String getCity() {
		return city;
	}

	/**
	 * City
	 *
	 * @param city
	 *            City
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Region within a country. State, province, prefecture or region component.
	 *
	 * @return Region within a country. State, province, prefecture or region
	 *         component.
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Region within a country. State, province, prefecture or region component.
	 *
	 * @param region
	 *            Region within a country. State, province, prefecture or region
	 *            component.
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Country code or name
	 *
	 * @return Country code or name
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Country code or name
	 *
	 * @param country
	 *            Country code or name
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Postal Code
	 *
	 * @return Postal Code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Postal Code
	 *
	 * @param postalCode
	 *            Postal Code
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for Address
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String streetAddress = null;
		private String city = null;
		private String region = null;
		private String country = null;
		private String postalCode = null;

		public Builder() {
		}

		public Builder streetAddress(String streetAddress) {
			this.streetAddress = streetAddress;
			return this;
		}

		public Builder city(String city) {
			this.city = city;
			return this;
		}

		public Builder region(String region) {
			this.region = region;
			return this;
		}

		public Builder country(String country) {
			this.country = country;
			return this;
		}

		public Builder postalCode(String postalCode) {
			this.postalCode = postalCode;
			return this;
		}

		public Address build() {
			return new Address(this);
		}

	}
}

