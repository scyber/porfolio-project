package com.backend.personalization.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.backend.personalization.model.Event;

@Component
public class KafkaEventPrducer implements EventProducer{
	
	private final KafkaTemplate<Long, Event> kafkaTemplate;
	
	@Value("${topics.events}")
	private String eventsTopic;
	
	public KafkaEventPrducer(KafkaTemplate kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public void produce(Event event) {
		this.kafkaTemplate.send(eventsTopic, event.getUserId(), event);
	}

}
