package com.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.handlers.KafkaSendException;
import com.example.model.RawUserEvent;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, RawUserEvent> kafkaTemplate;

    @Value("${kafka.topic.user-events}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<String, RawUserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(RawUserEvent event) {
        try {
            logger.info("Sending event to Kafka: {}", event);
            
            //ToDo Kafka events Minimum Once, or Exact Once or At least Once
            kafkaTemplate.send(topic, event.userId(), event).get();

            logger.info("Event successfully sent to Kafka");
        } catch (Exception e) {
            logger.error("Failed to send event to Kafka", e);
            throw new KafkaSendException("Failed to send event to Kafka", e);
        }
    }
}