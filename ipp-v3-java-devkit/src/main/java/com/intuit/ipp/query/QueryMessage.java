package com.intuit.ipp.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class to have query message
 * 
 */
public class QueryMessage {

	/**
	 * variable SQL
	 */
	private String sql = null;
	
	/**
	 * variable count
	 */
	private boolean count = false;
	
	/**
	 * variable Projection
	 */
	private List<String> projection = new ArrayList<String>();
	
	/**
	 * variable entity
	 */
	private String entity = null;
	
	/**
	 * variable whereClause
	 */
	private List<String> whereClause = new ArrayList<String>();
	
	/**
	 * variable startposition
	 */
	private Integer startposition = null;
	
	/**
	 * variable 
	 */
	private Integer maxresults = null;
	
	/**
	 * variable orderByClause
	 */
	private String orderByClause = null;

	/**
	 * Gets sql
	 * 
	 * @return sql
	 */
	public String getSQL() {
		return sql;
	}

	/**
	 * Sets sql
	 * @param sql
	 */
	public void setSQL(String sql) {
		this.sql = sql;
	}

	/**
	 * Gets projection
	 * 
	 * @return projection
	 */
	public List<String> getProjection() {
		return projection;
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
	 * @param entity
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * Gets whereClause
	 * 
	 * @return whereClause
	 */
	public List<String> getOptional() {
		return whereClause;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String toString() {
		if (getSQL() == null || getEntity() == null) {
			return null;
		}
		StringBuilder query = new StringBuilder();
		query.append(sql);

		if (isCount()) {
			query.append(" count(*)");
		} else if (getProjection().size() == 0) {
			query.append(" ").append("*");
		} else {
			boolean firstProjection = true;
			Iterator<String> iter = getProjection().iterator();
			while (iter.hasNext()) {
				String projectionStr = iter.next();
				if (firstProjection) {
					query.append(" ").append(projectionStr);
					firstProjection = false;
				} else {
					query.append(", ").append(projectionStr);
				}
			}
		}
		query.append(" FROM ").append(getEntity());
		if (getOptional() != null) {
			Boolean firstOptional = true;
			Iterator<String> iter = getOptional().iterator();
			while (iter.hasNext()) {
				String optional = iter.next();
				if (firstOptional) {
					query.append(" WHERE ").append(optional);
					firstOptional = false;
				} else {
					query.append(" AND ").append(optional);
				}
			}
		}

		if (getOrderByClause() != null) {
			query.append(" ORDERBY ").append(getOrderByClause());
		}

		if (startposition != null) {
			query.append(" STARTPOSITION " + (getStartposition().intValue() + 1));
		}
		if (maxresults != null) {
			query.append(" MAXRESULTS " + getMaxresults());

		}
		return query.toString();
	}

	/**
	 * Gets orderByClause
	 * 
	 * @return orderByClause
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * Sets orderByClause
	 * @param orderByClause
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * Gets startposition
	 * 
	 * @return startposition
	 */
	public Integer getStartposition() {
		return startposition;
	}

	/**
	 * Sets startposition
	 * @param startposition
	 */
	public void setStartposition(Integer startposition) {
		this.startposition = startposition;
	}

	/**
	 * Gets maxresults
	 * 
	 * @return maxresults
	 */
	public Integer getMaxresults() {
		return maxresults;
	}

	/**
	 * Sets maxresults
	 * @param maxresults
	 */
	public void setMaxresults(Integer maxresults) {
		this.maxresults = maxresults;
	}

	/**
	 * Gets count
	 * 
	 * @return count
	 */
	public boolean isCount() {
		return count;
	}

	/**
	 * Sets count
	 * @param count
	 */
	public void setCount(boolean count) {
		this.count = count;
	}
}
