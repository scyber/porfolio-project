package com.example.serilisations;

import java.io.IOException;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDeserializationSchema<T> implements DeserializationSchema<T> {

    private final Class<T> targetClass;
    private transient ObjectMapper mapper;

    public JsonDeserializationSchema(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public void open(InitializationContext context) {
        mapper = new ObjectMapper();
    }

    @Override
    public T deserialize(byte[] message) throws IOException {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper.readValue(message, targetClass);
    }

    @Override
    public boolean isEndOfStream(T nextElement) {
        return false;
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return TypeInformation.of(targetClass);
    }
}
