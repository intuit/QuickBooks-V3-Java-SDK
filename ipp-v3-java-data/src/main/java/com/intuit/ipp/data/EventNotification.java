package com.intuit.ipp.data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventNotification {

	String realmId;
	DataChangeEvent dataChangeEvent;
	
	public String getRealmId() {
		return realmId;
	}
	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
	public DataChangeEvent getDataChangeEvent() {
		return dataChangeEvent;
	}
	public void setDataChangeEvent(DataChangeEvent dataChangeEvent) {
		this.dataChangeEvent = dataChangeEvent;
	}
	
	
}
