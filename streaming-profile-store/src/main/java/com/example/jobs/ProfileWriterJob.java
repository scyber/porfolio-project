package com.example.jobs;


import java.sql.PreparedStatement;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.EnrichedEvent;
import com.example.model.UserProfile;
import com.example.model.UserProfileUpdated;
import com.example.serializers.JsonDeserializationSchema;

//import org.apache.flink.connector.jdbc.core.datastream.sink.JdbcSink;
import org.apache.flink.connector.jdbc.*;


import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.*;

import java.sql.PreparedStatement;
import java.util.Properties;


public class ProfileWriterJob {

    private static final Logger logger = LoggerFactory.getLogger(ProfileWriterJob.class);
    private static final String userProfileUpdatesTopic = "user-profile-updates";
    private static final String BOOTSTRAP_SERVERS = "kafka-0:9092,kafka-1:9092,kafka-2:9092";
    private static final String GROUP_ID = "profile-writer-group";

    public static void main(String[] args) {
        logger.info("ProfileWriterJob started.");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(30_000L);

        KafkaSource<UserProfile> sourceUserProfilSource = KafkaSource.<UserProfile>builder()
                                .setBootstrapServers(BOOTSTRAP_SERVERS)
                                .setTopics(userProfileUpdatesTopic)
                                .setGroupId(GROUP_ID)
                                .setValueOnlyDeserializer(
                                                new JsonDeserializationSchema<>(UserProfile.class))
                                .build();

        logger.info("Kafka Source created for topic: {}", userProfileUpdatesTopic);
      
    //We alrady have it from Aggregator Job UserProfile object    
    DataStream<UserProfile> updatesDataStream = env.fromSource(sourceUserProfilSource, WatermarkStrategy.noWatermarks(), "profile-updates");

    // Пример простой парсинг → объект (id, score, interestsMap...)
    //DataStream<UserProfile> updates = raw.map(ProfileParsers::parseJsonToPojo);

    // 2) Redis Sink: HSET user:{id} поля профиля (идемпотентность)
    FlinkJedisPoolConfig redisCfg = new FlinkJedisPoolConfig
      .Builder().setHost("redis").setPort(6379).build();

    RedisMapper<Tuple2<String,String>> redisMapper = new RedisMapper<>() {
      @Override
      public RedisCommandDescription getCommandDescription() {
        // HSET <hash> <field> <value>
        return new RedisCommandDescription(RedisCommand.HSET, null);
      }
      @Override public String getKeyFromData(Tuple2<String,String> data){ return data.f0; }   // hash
      @Override public String getValueFromData(Tuple2<String,String> data){ return data.f1; } // "field:value" (см. ниже)
    };

    // Преобразуем профиль в список пар (hash="user:{id}", "field:value")
    //ToDo implement Redis sink
    // updates
    //   .flatMap(ProfileMappers::toRedisPairs) // → Tuple2<"user:{id}", "field:value">
    //   .addSink(new RedisSink<>(redisCfg, mapper))
    //   .name("redis-hset");

    // 3) Postgres Sink: UPSERT (idempotent)
    String upsertSql =
      "INSERT INTO user_profiles (user_id, activity_score, interests_json, last_seen) " +
      "VALUES (?, ?, ?::jsonb, ?) " +
      "ON CONFLICT (user_id) DO UPDATE SET " +
      "  activity_score = EXCLUDED.activity_score, " +
      "  interests_json = EXCLUDED.interests_json, " +
      "  last_seen = EXCLUDED.last_seen";

    //ToDo implement statement  
    // JdbcStatementBuilder<UserProfileUpdated> stmt = (PreparedStatement ps, UserProfileUpdated u) -> {
    //   ps.setString(1, u.userId());
    //   ps.setDouble(2, u.activityScore());
    //   ps.setString(3, u.interestsAsJson()); // сериализуем Map в JSON
    //   ps.setLong(4, u.lastSeen());
    // };

    JdbcExecutionOptions execOpts = JdbcExecutionOptions.builder()
      .withBatchSize(1000)
      .withBatchIntervalMs(200)
      .withMaxRetries(5)
      .build();

    JdbcConnectionOptions connOpts = new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
      .withUrl("jdbc:postgresql://postgres:5432/personalization")
      .withDriverName("org.postgresql.Driver")
      .withUsername("postgres-user")
      .withPassword("postgres-pw")
      .build();

    //ToDo  
    // Идемпотентный sink (at-least-once → effectively exactly-once благодаря UPSERT)
    // updates.sinkTo(
    //   JdbcSink.sink(upsertSql, stmt, execOpts, connOpts)
    // ).name("postgres-upsert");



        try {
            env.execute("Profile Writer Job start execution");
        } catch (Exception e) {
            logger.error("Error executing ProfileWriterJob {} ", e);
        }
        
    }
}