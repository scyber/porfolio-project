package com.backend.personalization.services;

import com.backend.personalization.model.Event;

public interface EventProducer {

	void produce(Event event);
}
