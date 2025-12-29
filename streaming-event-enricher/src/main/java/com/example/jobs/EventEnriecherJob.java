package com.example.jobs;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventEnriecherJob {
	
	private static Logger logger = LoggerFactory.getLogger(EventEnriecherJob.class);

	public static void main(String[] args) {
		logger.info("Job started");
		
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		// Use Resource Boundle
		final String MY_BOOTSTRAP_SERVERS = "localhost:9094";
		
		
		//ToDo add Kafka Source
		//ToDo add sink
		
		
		try {
			env.execute("EventEnriecherJob");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Failed to execute due to " + e.getStackTrace());
			e.printStackTrace();
		}
		
		
	}
}
