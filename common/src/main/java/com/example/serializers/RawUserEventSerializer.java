package com.example.serializers;

import org.apache.flink.api.common.serialization.SerializationSchema;
import com.example.model.RawUserEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RawUserEventSerializer implements SerializationSchema<RawUserEvent> {

    private static final long serialVersionUID = 1L;

    transient ObjectMapper mapper;

    @Override
    public void open(InitializationContext context) {
        mapper = new ObjectMapper().findAndRegisterModules();
    }

    @Override
    public byte[] serialize(RawUserEvent element) {
        try {
            return mapper.writeValueAsBytes(element);
        } catch (Exception e) {
            throw new RuntimeException("Serialization error", e);
        }
    }

}
