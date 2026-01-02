package com.example.serilisations;

import org.apache.flink.api.common.serialization.SerializationSchema;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializationSchema<T> implements SerializationSchema<T> {

    private transient ObjectMapper mapper;

    @Override
    public void open(InitializationContext context) {
        mapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(T element) {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        try {
            return mapper.writeValueAsBytes(element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize", e);
        }
    }
}
