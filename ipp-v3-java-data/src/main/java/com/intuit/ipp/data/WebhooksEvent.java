package com.intuit.ipp.data;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Wrapper class for the webhooks payload
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhooksEvent {

	List<EventNotification> eventNotifications;

	public List<EventNotification> getEventNotifications() {
		return eventNotifications;
	}

	public void setEventNotifications(List<EventNotification> eventNotifications) {
		this.eventNotifications = eventNotifications;
	}
	
}
