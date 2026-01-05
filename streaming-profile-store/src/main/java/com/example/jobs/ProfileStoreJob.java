package com.example.jobs;


import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileStoreJob {

    private static final Logger logger = LoggerFactory.getLogger(ProfileStoreJob.class);

    public static void main(String[] args) {
        logger.info("ProfileStoreJob started.");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();



        try {
            env.execute("Profile Store Job start execution");
        } catch (Exception e) {
            logger.error("Error executing ProfileStoreJob {} ", e);
        }
        
    }
}