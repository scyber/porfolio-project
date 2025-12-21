package com.example.jobs;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;

//ToDo for this Job would be generation Stream of Events Generated 
// Like Users orders on preferences

public class GeneratorJob {

	private static final Logger logger = LogManager.getLogger(GeneratorJob.class);
	private static final String BOOTSTRAP_SERVERS = "localhost:9094";

	public static void main(String[] args) {

		logger.info("Application started.");
		logger.debug("Debug info: args={}", (Object) args);
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		GeneratorFunction<Long, String> generatorFunction = index -> {
			String str = getRandomPhrase();
			return str;

		};

		DataGeneratorSource<String> source = new DataGeneratorSource<>(generatorFunction, Long.MAX_VALUE, Types.STRING);

		env.fromSource(source, WatermarkStrategy.forMonotonousTimestamps(),
				"Stream Generator").filter(
						value -> value.contains("This"))
				.sinkTo(KafkaSink.<String>builder().setBootstrapServers(BOOTSTRAP_SERVERS)
						.setRecordSerializer(KafkaRecordSerializationSchema.builder().setTopic("topic1")
								.setValueSerializationSchema(new SimpleStringSchema()).build())
						.build());

		try {
			logger.info("Executing Generator job. starting now...");
			env.execute("Generator Data Job");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static String getRandomPhrase() {

		final Map<Long, String> frases = new HashMap<>();
		frases.put(0L, "I like this Lake");
		frases.put(2L, "Hey Jude");
		frases.put(1L, "This movie is Facke");
		frases.put(3L, "This a is Brad Pit hero");
		frases.put(4L, "This a is Scarlet Jochanson bady");
		frases.put(5L, "This a is Chack Norris agains Bruce Lee");
		frases.put(6L, "Oh, this Arnold Schrazneger boy");
		frases.put(7L, "Hi, Angelina Jolly ");
		frases.put(8L, "All world in your pocket");
		frases.put(9L, "I like this place");
		frases.put(10L, "Cut off your Face in Paris basted!");

		long index = new Random().nextInt(9);
		String res = frases.getOrDefault(index, "Default Value response " + index);

		return res;

	}

}
