package com.example.serializers;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.EnrichedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EnrichEventSerializer implements SerializationSchema<EnrichedEvent> {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(EnrichEventSerializer.class);
    transient ObjectMapper mapper;

    @Override
    public void open(InitializationContext context) {
        mapper = new ObjectMapper().findAndRegisterModules();
    }

    @Override
    public byte[] serialize(EnrichedEvent element) {
        try {
            return mapper.writeValueAsBytes(element);
        } catch (Exception e) {
            logger.error("Serialization error: {} " + e);
            throw new RuntimeException("Serialization error", e);
        }
    }

}
