package com.backend.personalization.services;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.backend.personalization.model.Event;


@Service
public class EventServiceImpl implements EventService{

	private final KafkaEventPrducer producer;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EventService.class);
	
	public EventServiceImpl(KafkaEventPrducer producer) {
		this.producer = producer;
	}
	
	@Override
	public void processEvent(Event event) {
		// TODO Auto-generated method stub
		logger.debug("Produce event", event);
		this.producer.produce(event);
		
	}

}
