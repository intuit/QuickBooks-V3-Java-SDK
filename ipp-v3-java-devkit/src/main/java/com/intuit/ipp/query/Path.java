/*******************************************************************************
 * Copyright (c) 2017 Intuit
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
package com.intuit.ipp.query;


/**
 * Path will be the return type when it is not leaf node. these will be used for
 * listing properties in FROM context.
 * 
 */
public class Path<T> {

	/**
	 * variable entity
	 */
	private String entity = null;
	
	/**
	 * variable path string
	 */
	private String pathString = null;

	/**
	 * Constructor Path
	 * 
	 * @param path the path
	 * @param entity the entity
	 */
	public Path(String path, String entity) {
		this.pathString = path;
		this.entity = entity;
	}

	/**
	 * Gets path string
	 * 
	 * @return pathString
	 */
	public String getPathString() {
		return pathString;
	}

	/**
	 * Sets pathString
	 * 
	 * @param pathString
	 */
	public void setPathString(String pathString) {
		this.pathString = pathString;
	}

	@Override
	public String toString() {
		return pathString;
	}

	/**
	 * Gets entity
	 * 
	 * @return entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * Sets entity
	 * 
	 * @param entity
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}
}
