package com.backend.personalization.services;

import org.springframework.stereotype.Service;

import com.backend.personalization.model.Event;


public interface EventService {

	void processEvent(Event event);


}
