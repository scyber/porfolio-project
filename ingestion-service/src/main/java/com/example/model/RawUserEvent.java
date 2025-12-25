package com.example.model;


public record RawUserEvent(String eventId,
		
		String userId,
		EventType eventType,
		long timestamp,
		EventContext context){
	

}
