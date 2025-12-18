package com.example.jobs;

import java.util.List;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ververica.cdc.connectors.postgres.source.PostgresSourceBuilder;
import com.ververica.cdc.connectors.postgres.source.PostgresSourceBuilder.PostgresIncrementalSource;
import com.ververica.cdc.connectors.postgres.source.utils.CustomPostgresSchema;





public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		logger.info("Application started.");
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		
		 GeneratorFunction<Long, String> generatorFunction = index -> "Number: " + index;

//		 DataGeneratorSource<String> source =
//		         new DataGeneratorSource<>(generatorFunction, 10_000_000, Types.STRING);

		 DataGeneratorSource<String> source =
		         new DataGeneratorSource<>(
		              generatorFunction,
		              100L,
		              RateLimiterStrategy.perSecond(10),
		              Types.STRING);
		 
		 

		
        logger.debug("Debug info: args={}", (Object) args);

		 
		 DataStreamSource<String> stream =
		         env.fromSource(source,
		         WatermarkStrategy.noWatermarks(),
		         "Generator Source");
		
		

        env.fromSource(source, WatermarkStrategy.noWatermarks(), "Sample 1Mi String Generator")
           .map(value -> {
        	   logger.info("Processing value: {}", value);
        	   return "Value: " + value;
           })
           .print();

        try {
        	logger.info("Executing Flink job. starting now...");
			env.execute("Modern Streaming Job");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
