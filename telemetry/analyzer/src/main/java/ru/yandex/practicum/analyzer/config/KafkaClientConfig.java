package ru.yandex.practicum.analyzer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.analyzer.deserializer.HubEventDeserializer;
import ru.yandex.practicum.analyzer.deserializer.SensorsSnapshotDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "analyzer.kafka")
public class KafkaClientConfig {

    @Bean
    public KafkaConsumer<String, HubEventAvro> createHubEventConsumer(
            @Value("${kafka.bootstrap-servers}") String bootstrapServers) {
        Properties props = buildCommonConsumerProperties(bootstrapServers, "hub-event-consumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "hub-analyzer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, HubEventDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 3_072_000);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 307200);

        log.info("Created Kafka consumer for Hub Events: {}", bootstrapServers);
        return new KafkaConsumer<>(props);
    }

    @Bean
    public KafkaConsumer<String, SensorsSnapshotAvro> createSensorsSnapshotConsumer(
            @Value("${kafka.bootstrap-servers}") String bootstrapServers) {
        Properties props = buildCommonConsumerProperties(bootstrapServers, "sensors-snapshot-consumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "snapshot-analyzer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SensorsSnapshotDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 3_072_000);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 307200);

        log.info("Created Kafka consumer for Sensors Snapshots: {}", bootstrapServers);
        return new KafkaConsumer<>(props);
    }

    private Properties buildCommonConsumerProperties(String bootstrapServers, String clientId) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return properties;
    }

    @Bean
    @Qualifier("snapshotTopic")
    public String getSnapshotTopic(@Value("${kafka.topic.snapshot}") String snapshotTopic) {
        log.info("Configured topic for hub snapshots: {}", snapshotTopic);
        return snapshotTopic;
    }

    @Bean
    @Qualifier("hubTopic")
    public String getHubTopic(@Value("${kafka.topic.hub}") String hubTopic) {
        log.info("Configured topic for sensors: {}", hubTopic);
        return hubTopic;
    }
}