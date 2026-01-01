package com.example.serializers;

import java.io.IOException;

//import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import com.example.model.RawUserEvent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RawUserEventDeserializer implements DeserializationSchema<RawUserEvent> {

	private static final long serialVersionUID = 1L;

	private transient ObjectMapper mapper;
	private Logger logger = LoggerFactory.getLogger(RawUserEventDeserializer.class);

	@Override
	public void open(InitializationContext context) {
		this.mapper = new ObjectMapper()
				.findAndRegisterModules()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
	}

	@Override
	public RawUserEvent deserialize(byte[] message) throws IOException {
		logger.info("Deserializing RawUserEvent {}", new String(message));
		try {
			var result = mapper.readValue(message, RawUserEvent.class);
			logger.info("Deserialized RawUserEvent {}", result);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Failed to read Serialized value {}", e);
			throw e;
		}
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
