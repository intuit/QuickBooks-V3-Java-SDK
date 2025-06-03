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
public class CheckContext extends Entity {

	private static final long serialVersionUID = 1L;

	private DeviceInfo deviceInfo = null;

	public CheckContext() {
	}

	private CheckContext(Builder builder) {
		this.deviceInfo = builder.deviceInfo;
	}

	/**
	 * @return
	 */
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * @param deviceInfo
	 */
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * Builder class for CheckContext
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private DeviceInfo deviceInfo = null;

		public Builder(DeviceInfo deviceInfo) {
			this.deviceInfo = deviceInfo;
		}

		public CheckContext build() {
			return new CheckContext(this);
		}

	}
}

