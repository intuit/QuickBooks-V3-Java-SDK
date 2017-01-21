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
