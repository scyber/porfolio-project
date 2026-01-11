package com.example.configs;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.example.model.RawUserEvent;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.properties.retries}")
    private Integer producerMaxRetryTimes;

    @Value("${spring.kafka.producer.properties.acks}")
    private String AskConfig;

    @Value("${spring.kafka.producer.properties.retries}")
    private Integer retries;

    @Bean
    public ProducerFactory<String, RawUserEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Exactly Once
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        config.put(ProducerConfig.ACKS_CONFIG, AskConfig);
        // Add maximum Retry times
        config.put(ProducerConfig.RETRIES_CONFIG, retries);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);

        // Transactions
        config.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "user-event-tx-id");

        // exclude Jackson type headers
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        DefaultKafkaProducerFactory<String, RawUserEvent> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(
                config);

        kafkaProducerFactory.setTransactionIdPrefix("tx-");

        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, RawUserEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
