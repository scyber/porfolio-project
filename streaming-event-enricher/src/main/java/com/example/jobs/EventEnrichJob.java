package com.example.jobs;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.functions.EnrichFunction;
import com.example.model.CategoryEvent;
import com.example.model.EnrichedEvent;
import com.example.model.Enrichment;
import com.example.model.EventContext;
import com.example.model.RawUserEvent;
import com.example.serializers.EnrichEventSerializer;
import com.example.serializers.RawUserEventDeserializer;

public class EventEnrichJob {

	private static final Logger logger = LoggerFactory.getLogger(EventEnrichJob.class);
	private static EnrichFunction enrichFunction = new EnrichFunction();

	public static void main(String[] args) {
		logger.info("Job started");

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// ToDo rework Use Resource Boundle
		final String MY_BOOTSTRAP_SERVERS = "localhost:9094";
		final String rawUserEventsTopic = "raw-user-events";
		final String enriechedUserEventsTopic = "enrieched-user-events";

		// ToDo
		KafkaSource<RawUserEvent> source = KafkaSource.<RawUserEvent>builder()
				.setBootstrapServers(MY_BOOTSTRAP_SERVERS)
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
						.setBootstrapServers(MY_BOOTSTRAP_SERVERS)
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
