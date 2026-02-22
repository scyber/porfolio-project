package com.example.model;

import java.util.UUID;

public class RawUserEvent {

	private UUID eventId;
	private String userId;
	private EventType eventType;
	private Long timestamp;
	private EventContext context;

	public RawUserEvent() {
	}

	public RawUserEvent(UUID eventId, String userId, EventType eventType, Long timestamp, EventContext context) {
		this.eventId = eventId;
		this.userId = userId;
		this.eventType = eventType;
		this.timestamp = timestamp;
		this.context = context;
	}

	public UUID getEventId() {
		return eventId;
	}

	public void setEventId(UUID eventId) {
		this.eventId = eventId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public EventContext getContext() {
		return context;
	}

	public void setContext(EventContext context) {
		this.context = context;
	}
}