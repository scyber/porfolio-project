package com.example.jobs;

import java.util.List;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {

	public static void main(String[] args) {
		
		
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		
		 GeneratorFunction<Long, String> generatorFunction = index -> "Number: " + index;

		 DataGeneratorSource<String> source =
		         new DataGeneratorSource<>(generatorFunction, 10_000_000, Types.STRING);

		 DataStreamSource<String> stream =
		         env.fromSource(source,
		         WatermarkStrategy.noWatermarks(),
		         "Generator Source");
		
		

        env.fromSource(source, WatermarkStrategy.noWatermarks(), "Sample 1Mi String Generator")
           .map(value -> "Value: " + value)
           .print();

        try {
			env.execute("Modern Streaming Job");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
