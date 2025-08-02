package ru.yandex.practicum.aggregator.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.deserializer.SensorEventDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Component
public class KafkaAvroConsumer {

    private final Consumer<String, SensorEventAvro> consumer;

    public KafkaAvroConsumer(@Value("${topic.sensor-events}") String topic) {
        Properties config = new Properties();

        config.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SensorEventDeserializer.class);

        this.consumer = new KafkaConsumer<>(config);
        this.consumer.subscribe(List.of(topic));

        Thread thread = new Thread(this::consumeMessages);
        thread.setDaemon(true);
        thread.start();
    }

    private void consumeMessages() {
        try {
            while (true) {
                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, SensorEventAvro> record : records) {
                    log.info("Received message from partition {}, offset {}:\n{}\n",
                            record.partition(), record.offset(), record.value());
                }
            }
        } catch (Exception e) {
            log.error("Error while consuming messages", e);
        } finally {
            consumer.close();
        }
    }
}