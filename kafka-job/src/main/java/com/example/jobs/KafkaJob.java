package com.example.jobs;

import java.util.HashSet;
import java.util.Set;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.KafkaSourceBuilder;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaJob {

	private static Logger logger = LoggerFactory.getLogger(KafkaJob.class);
	static final Set<String> filterWords = new HashSet<>();
	
	public static void main(String[] args) throws Exception {
		logger.info("Kafka Job started.");

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		final String MY_BOOTSTRAP_SERVERS = "localhost:9094";
		
		filterWords.add("Boby");
		filterWords.add("Vasil");
		filterWords.add("John");
		
		KafkaSource<String> source = KafkaSource.<String>builder()
			    .setBootstrapServers("localhost:9094")
			    .setTopics("topic1")
			    .setGroupId("my-group")
			    .setStartingOffsets(OffsetsInitializer.latest())
			    .setValueOnlyDeserializer(new SimpleStringSchema())
			    .build();
		
		DataStream<String> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "KafkaSource");
		
		
			env.fromSource(source, WatermarkStrategy.noWatermarks(), "Sample Kafka Transformer String ")
	            //.filter(value -> filterWords.contains(value))
				.map(value -> {
	        	   logger.info("Processing value: {}", value);
	        	   return "Processed Value: " + value;
	           }).sinkTo(KafkaSink.<String>builder()
	        		   .setBootstrapServers(MY_BOOTSTRAP_SERVERS)
	        		   .setRecordSerializer(
	        				   KafkaRecordSerializationSchema.builder()
	        				   .setTopic("topic2")
	        				   .setValueSerializationSchema(new SimpleStringSchema())
	        		   .build()).build());
	        		   

		
		env.execute("Kafka Job");
			
		
		

	}

}
