package com.example.functions;

import java.util.Properties;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import com.example.logger.KafkaLogger;
import com.example.model.EnrichedEvent;
import com.example.model.RawUserEvent;

public class EnrichFunction extends RichMapFunction<RawUserEvent, EnrichedEvent> {

    private static final long serialVersionUID = 1L;
	private transient KafkaLogger logger;

    public void open(Configuration parameters) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        logger = new KafkaLogger("enricher-logs", props);
    }

    @Override
    public EnrichedEvent map(RawUserEvent userEvent) {
        logger.log("Processing event: " + userEvent.eventId());
        return enrich(userEvent);
    }

    private EnrichedEvent enrich(RawUserEvent userEvent) {
		// TODO Auto-generated method stub
    	// Imlement convertion from RawUserEvent to EnrichmentEvent
		return null;
	}

	@Override
    public void close() {
        logger.close();
    }
}