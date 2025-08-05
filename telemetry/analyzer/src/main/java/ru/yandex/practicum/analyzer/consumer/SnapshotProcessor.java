package ru.yandex.practicum.analyzer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.service.snapshot.SnapshotService;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SnapshotProcessor {

    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    private final KafkaConsumer<String, SensorsSnapshotAvro> kafkaSensorsSnapshotConsumer;
    private final String snapshotTopic;
    private final SnapshotService snapshotService;

    public SnapshotProcessor(KafkaConsumer<String, SensorsSnapshotAvro> kafkaSensorsSnapshotConsumer,
                             @Qualifier("snapshotTopic") String snapshotTopic,
                             SnapshotService snapshotService) {
        this.kafkaSensorsSnapshotConsumer = kafkaSensorsSnapshotConsumer;
        this.snapshotTopic = snapshotTopic;
        this.snapshotService = snapshotService;
    }

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaSensorsSnapshotConsumer::wakeup));
        try {
            kafkaSensorsSnapshotConsumer.subscribe(List.of(snapshotTopic));

            while (true) {
                ConsumerRecords<String, SensorsSnapshotAvro> records = kafkaSensorsSnapshotConsumer.poll(Duration.ofSeconds(4));
                int count = 0;

                for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
                    handleRecord(record);
                    manageOffsets(record, count, kafkaSensorsSnapshotConsumer);
                    count++;
                }

                kafkaSensorsSnapshotConsumer.commitAsync();
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Error during snapshot processing", e);
        } finally {
            try {
                kafkaSensorsSnapshotConsumer.commitSync(currentOffsets);
            } catch (Exception e) {
                log.warn("Error during final offset commit", e);
            }
            log.info("Closing snapshot consumer");
            kafkaSensorsSnapshotConsumer.close();
        }
    }

    private static void manageOffsets(ConsumerRecord<String, SensorsSnapshotAvro> record,
                                      int count,
                                      KafkaConsumer<String, SensorsSnapshotAvro> consumer) {
        currentOffsets.put(
                new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1)
        );

        if (count % 10 == 0) {
            consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                if (exception != null) {
                    log.warn("Error during offset commit: {}", offsets, exception);
                }
            });
        }
    }

    private void handleRecord(ConsumerRecord<String, SensorsSnapshotAvro> record) {
        snapshotService.handleSnapshot(record.value());
    }
}