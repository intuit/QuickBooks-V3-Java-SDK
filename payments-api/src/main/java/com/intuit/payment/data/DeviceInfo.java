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
public class DeviceInfo extends Entity {

	private static final long serialVersionUID = 1L;

	private String id = null;
	private String type = null;
	private String longitude = null;
	private String latitude = null;
	private Boolean encrypted = null;
	private String phoneNumber = null;
	private String macAddress = null;
	private String ipAddress = null;

	public DeviceInfo() {
	}

	private DeviceInfo(Builder builder) {
		this.id = builder.id;
		this.type = builder.type;
		this.longitude = builder.longitude;
		this.latitude = builder.latitude;
		this.encrypted = builder.encrypted;
		this.phoneNumber = builder.phoneNumber;
		this.macAddress = builder.macAddress;
		this.ipAddress = builder.ipAddress;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
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

	/**
	 * @return
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @param macAddress
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	/**
	 * @return
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return
	 */
	public Boolean getEncrypted() {
		return encrypted;
	}

	/**
	 * @param encrypted
	 */
	public void setEncrypted(Boolean encrypted) {
		this.encrypted = encrypted;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for DeviceInfo
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String id = null;
		private String type = null;
		private String longitude = null;
		private String latitude = null;
		private Boolean encrypted = null;
		private String phoneNumber = null;
		private String macAddress = null;
		private String ipAddress = null;

		public Builder() {
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder longitude(String longitude) {
			this.longitude = longitude;
			return this;
		}

		public Builder latitude(String latitude) {
			this.latitude = latitude;
			return this;
		}

		public Builder encrypted(Boolean encrypted) {
			this.encrypted = encrypted;
			return this;
		}

		public Builder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public Builder macAddress(String macAddress) {
			this.macAddress = macAddress;
			return this;
		}

		public Builder ipAddress(String ipAddress) {
			this.ipAddress = ipAddress;
			return this;
		}

		public DeviceInfo build() {
			return new DeviceInfo(this);
		}

	}

}

