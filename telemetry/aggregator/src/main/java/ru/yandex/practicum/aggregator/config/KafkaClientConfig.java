package ru.yandex.practicum.aggregator.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Configuration
public class KafkaClientConfig {

    private final KafkaPropertiesConfig kafkaProperties;

    public KafkaClientConfig(KafkaPropertiesConfig kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public KafkaConsumer<String, SensorEventAvro> kafkaConsumer() {
        Properties props = new Properties();
        props.putAll(kafkaProperties.getConsumer());
        return new KafkaConsumer<>(props);
    }

    @Bean
    public Producer<String, SensorsSnapshotAvro> kafkaProducer() {
        Properties props = new Properties();
        props.putAll(kafkaProperties.getProducer());
        return new KafkaProducer<>(props);
    }
}
