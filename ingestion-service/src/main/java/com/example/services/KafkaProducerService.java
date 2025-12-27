package com.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.handlers.KafkaSendException;
import com.example.model.RawUserEvent;

import java.util.concurrent.TimeUnit;

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
            
            kafkaTemplate.send(topic, event.userId(), event).get(1000, TimeUnit.MILLISECONDS);

            logger.info("Event successfully sent to Kafka");
        } catch (Exception e) {
            logger.error("Failed to send event to Kafka", e);
            throw new KafkaSendException("Failed to send event to Kafka", e);
        }
    }
}