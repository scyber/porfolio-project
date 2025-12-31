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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.example.model.EnrichedEvent;
import com.example.model.RawUserEvent;
import com.example.serializers.RawUserEvnetDeserializer;

public class EventEnriecherJob {
	
	private static Logger logger = LoggerFactory.getLogger(EventEnriecherJob.class);

	public static void main(String[] args) {
		logger.info("Job started");
		
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		// ToDo rework Use Resource Boundle
		final String MY_BOOTSTRAP_SERVERS = "localhost:9094";
		final String rawUserEventsTopic = "raw-user-events";
		final String enriechedUserEventsTopic = "entrieched-user-events";
		
		//ToDo
		KafkaSource<RawUserEvent> source = KafkaSource.<RawUserEvent>builder()
			    .setBootstrapServers(MY_BOOTSTRAP_SERVERS)
			    .setTopics("rawUserEventsTopic")
			    .setGroupId("my-group")
			    .setStartingOffsets(OffsetsInitializer.latest())
			    .setValueOnlyDeserializer(new RawUserEvnetDeserializer())
			    .build();
		
		
		
		
		//ToDo add Kafka Source
		//ToDo add sink
		env.fromSource(source, WatermarkStrategy.noWatermarks(), "Sample Kafka Transformer String ")
        //.filter(value -> filterWords.contains(value))
		.map(value -> {
    	   logger.info("Processing value: {}", value);
    	   return "Processed Value: " + value;
       }).print();
		
		
		
		
		try {
			env.execute("EventEnriecherJob");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Failed to execute due to " + e.getStackTrace());
			e.printStackTrace();
		}
		
		
	}
}
