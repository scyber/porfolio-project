package com.example.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.handler.KafkaSendException;
import com.example.model.RawUserEvent;

@Service
public class KafkaProducerService implements IngestionService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, RawUserEvent> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<String, RawUserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void sendEvent(RawUserEvent event) {
        try {
            logger.info("Sending event to Kafka: {}", event);
            enrichEvent(event);

            kafkaTemplate.send(topic, event).get(1000, TimeUnit.MILLISECONDS);

            logger.info("Event successfully sent to Kafka");
        } catch (Exception e) {
            logger.error("Failed to send event to Kafka", e);
            throw new KafkaSendException("Failed to send event to Kafka", e);
        }
    }

    private void enrichEvent(RawUserEvent event) {
        // Placeholder for future enrichment logic
        if (event.getEventId() == null || event.getEventId().toString().isEmpty()) {
            event.setEventId(java.util.UUID.randomUUID());
        }
        if (event.getTimestamp() == null || event.getTimestamp() == 0) {
            event.setTimestamp(System.currentTimeMillis());
        }
    }
}