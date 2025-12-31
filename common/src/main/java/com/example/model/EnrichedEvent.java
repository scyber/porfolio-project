package com.example.model;

import java.sql.Timestamp;
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
public record EnrichedEvent(UUID eventId, String userId, EventType eventType, Long timestamp,
		EventContext eventContext, Enrichment enrichment) {

}
