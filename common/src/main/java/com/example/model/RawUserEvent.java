package com.example.model;

import java.sql.Timestamp;
import java.util.UUID;

public record RawUserEvent(UUID eventId,

		String userId, EventType eventType, Long timestamp, EventContext context) {

}
