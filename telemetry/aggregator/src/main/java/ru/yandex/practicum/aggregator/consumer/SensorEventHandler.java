package ru.yandex.practicum.aggregator.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.producer.SensorsSnapshotProducer;
import ru.yandex.practicum.aggregator.service.AggregationService;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SensorEventHandler {

    private final AggregationService aggregationService;
    private final SensorsSnapshotProducer producer;
    private final KafkaConsumer<String, SensorEventAvro> consumer;

    private final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    public void handle(ConsumerRecords<String, SensorEventAvro> records) {
        for (ConsumerRecord<String, SensorEventAvro> record : records) {
            aggregationService.aggregateEvent(record.value())
                    .ifPresent(snapshot ->
                            producer.send("telemetry.snapshots.v1", snapshot.getHubId(), snapshot)
                    );

            currentOffsets.put(
                    new TopicPartition(record.topic(), record.partition()),
                    new OffsetAndMetadata(record.offset() + 1)
            );
        }

        commitOffsets();
    }

    private void commitOffsets() {
        consumer.commitAsync(currentOffsets, null);
    }

    public void shutdown() {
        consumer.commitSync(currentOffsets);
        consumer.close();
    }
}