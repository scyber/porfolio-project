package com.backend.personalization.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.personalization.model.Event;
import com.backend.personalization.services.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/events")
public class EventsController {
	
	private final EventService eventService ;
	

	public EventsController(EventService eventService) {
		this.eventService = eventService;
	}
	
	public ResponseEntity<String> geceiveEvent(@RequestBody Event event){
	
		
		eventService.processEvent(event);
		return ResponseEntity.ok("Event Recived");
	}
	
}
