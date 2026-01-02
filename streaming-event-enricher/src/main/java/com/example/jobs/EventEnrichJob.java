package com.example.jobs;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.functions.EnrichFunction;
import com.example.model.EnrichedEvent;
import com.example.model.RawUserEvent;
import com.example.serializers.EnrichEventSerializer;
import com.example.serializers.RawUserEventDeserializer;

public class EventEnrichJob {

	private static final Logger logger = LoggerFactory.getLogger(EventEnrichJob.class);
	private static EnrichFunction enrichFunction = new EnrichFunction();

	public static void main(String[] args) {
		logger.info("Job EventEnrichJob started");

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// ToDo rework Use Resource Boundle
		final String 
		BOOTSTRAP_SERVERS = "kafka-0:9092,kafka-1:9092,kafka-2:9092";
		final String rawUserEventsTopic = "raw-user-events";
		final String enriechedUserEventsTopic = "enrieched-user-events";

		// ToDo
		KafkaSource<RawUserEvent> source = KafkaSource.<RawUserEvent>builder()
				.setBootstrapServers(BOOTSTRAP_SERVERS)
				.setTopics(rawUserEventsTopic)
				.setGroupId("enricher-group")
				// .setStartingOffsets(OffsetsInitializer.latest())
				.setStartingOffsets(OffsetsInitializer.earliest())
				// .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
				.setValueOnlyDeserializer(new RawUserEventDeserializer())
				.build();

		// ToDo add Kafka Source
		// ToDo add sink
		env.fromSource(source, WatermarkStrategy.noWatermarks(), "Raw User Events Source")
				// .filter(value -> filterWords.contains(value))

				.map(enrichFunction)
				.map(value -> {
					logger.info("Process Enrich Event {}", value);
					return value;
				})
				// .print();

				.sinkTo(KafkaSink.<EnrichedEvent>builder()
						.setBootstrapServers(BOOTSTRAP_SERVERS)
						.setRecordSerializer(
								KafkaRecordSerializationSchema.builder()
										.setTopic(enriechedUserEventsTopic)
										.setValueSerializationSchema(new EnrichEventSerializer())
										.build())
						.build());

		try {
			env.execute("EventEnriecherJob");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Failed to execute due to " + e.getStackTrace());
			e.printStackTrace();
		}

	}
}
