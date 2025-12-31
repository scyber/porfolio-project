package com.example.model;

import java.util.UUID;

public record RawUserEvent(UUID eventId,

		String userId, EventType eventType, Long timestamp, EventContext context) {

}
