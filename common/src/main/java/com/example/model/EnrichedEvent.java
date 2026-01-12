package com.example.model;

import java.util.UUID;

/*
 * {
  "eventId": "uuid",
  "userId": "string",
  "eventType": "string",
  "timestamp": 1714060800000,
  "context": {
    "page": "string",
    "itemId": "string",
    "category": "string",
    "device": "string",
    "geo": "string"
  },
  "enrichment": {
    "categoryVector": ["sports", "tech", "books"],
    "deviceType": "mobile",
    "geoRegion": "EU"
  }
}

 */

public class EnrichedEvent {

	private UUID eventId;
	private String userId;
	private EventType eventType;
	private Long timestamp;
	private EventContext context;
	private Enrichment enrichment;

	// Required by Flink
	public EnrichedEvent() {
	}

	public EnrichedEvent(UUID eventId, String userId, EventType eventType, Long timestamp, EventContext context,
			Enrichment enrichment) {
		this.eventId = eventId;
		this.userId = userId;
		this.eventType = eventType;
		this.timestamp = timestamp;
		this.context = context;
		this.enrichment = enrichment;
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

	public Enrichment getEnrichment() {
		return enrichment;
	}

	public void setEnrichment(Enrichment enrichment) {
		this.enrichment = enrichment;
	}
}