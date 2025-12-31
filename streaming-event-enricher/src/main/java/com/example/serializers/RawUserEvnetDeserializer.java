package com.example.serializers;

import java.io.IOException;

//import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import com.example.model.RawUserEvent;
import com.fasterxml.jackson.databind.ObjectMapper;




public class RawUserEvnetDeserializer implements DeserializationSchema<RawUserEvent> {

    private static final long serialVersionUID = 1L;

    private transient ObjectMapper mapper;

    @Override
    public void open(InitializationContext context) {
        this.mapper = new ObjectMapper();
    }

    @Override
    public RawUserEvent deserialize(byte[] message) throws IOException {
        return mapper.readValue(message, RawUserEvent.class);
    }

    @Override
    public boolean isEndOfStream(RawUserEvent nextElement) {
        return false;
    }

    @Override
    public TypeInformation<RawUserEvent> getProducedType() {
        return TypeInformation.of(RawUserEvent.class);
    }
}
