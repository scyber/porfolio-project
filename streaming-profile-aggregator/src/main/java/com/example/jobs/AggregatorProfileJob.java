package com.example.jobs;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.flink.connector.base.*; //DeliveryGuarantee; need to add dependency
import com.example.functions.UserProfileAggregatorFunction;
import com.example.model.EnrichedEvent;
import com.example.model.UserProfileUpdated;
import com.example.serilisations.JsonDeserializationSchema;
import com.example.serilisations.JsonSerializationSchema;

public class AggregatorProfileJob {

        private static final Logger logger = LoggerFactory.getLogger(AggregatorProfileJob.class);

        public static void main(String[] args) {

                final String enriechedUserEventsTopic = "enrieched-user-events";

                logger.info("Aggregator Profile Job started");
                // Implementation goes here

                StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
                env.enableCheckpointing(60_000L);

                // 2. Kafka source: enriched-events (JSON -> EnrichedEvent)
                KafkaSource<EnrichedEvent> source = KafkaSource.<EnrichedEvent>builder()
                                .setBootstrapServers("kafka-0:9092,kafka-1:9092,kafka-2:9092")
                                .setTopics("enrieched-user-events")
                                .setGroupId("user-profile-aggregator")
                                .setValueOnlyDeserializer(
                                                new JsonDeserializationSchema<>(EnrichedEvent.class))
                                .build();
                logger.info("Kafka Source created for topic: {}", enriechedUserEventsTopic);

                DataStreamSource<EnrichedEvent> eventsStream = env.fromSource(source, WatermarkStrategy.noWatermarks(),
                                "enriched-events-source");
                logger.info("DataStreamSource created from Kafka source {} ", eventsStream);
                // 3. Aggregation pipeline
                SingleOutputStreamOperator<UserProfileUpdated> profiles = eventsStream
                                .keyBy(EnrichedEvent::getUserId)
                                .process(new UserProfileAggregatorFunction())
                                .name("user-profile-aggregator");
                logger.info("UserProfile aggregation pipeline created: {}", profiles);
                // 4. Kafka sink: user-profile-updates (UserProfileUpdated -> JSON)
                KafkaSink<UserProfileUpdated> sink = KafkaSink.<UserProfileUpdated>builder()
                                .setBootstrapServers("kafka-0:9092,kafka-1:9092,kafka-2:9092")
                                .setRecordSerializer(
                                                KafkaRecordSerializationSchema.<UserProfileUpdated>builder()
                                                                .setTopic("user-profile-updates")
                                                                .setValueSerializationSchema(
                                                                                new JsonSerializationSchema<>())
                                                                .build())
                                // .setDeliverGuarantee(
                                // DeliveryGuarantee.AT_LEAST_ONCE)
                                .build();
                logger.info("Kafka Sink created for topic: user-profile-updates");
                profiles.sinkTo(sink).name("user-profile-updates-sink");

                // 5. Execute
                try {
                        env.execute("User Profile Aggregator Job");
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        logger.error("Error executing the Flink job {}", e);
                }
        }
}