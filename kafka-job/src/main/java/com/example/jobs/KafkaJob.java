package com.example.jobs;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.KafkaSourceBuilder;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaJob {

	private static Logger logger = LoggerFactory.getLogger(KafkaJob.class);

	public static void main(String[] args) {
		logger.info("Kafka Job started.");

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		final String MY_BOOTSTRAP_SERVERS = "localhost:9094";

		KafkaSource<String> kafkaSource = KafkaSource.<String>builder().setBootstrapServers(MY_BOOTSTRAP_SERVERS)
				.setTopics("topic1", "topic2")
				.setDeserializer(KafkaRecordDeserializationSchema.valueOnly(StringDeserializer.class)).build();

		env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source").map(value -> {
			logger.info("Processing value from Kafka: {}", value);
			return "Kafka Value: " + value;
		}).print();

		
		try {
			env.execute("KafkaJob");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error executing KafkaJob", e);
			e.printStackTrace();
		}

	}

}
