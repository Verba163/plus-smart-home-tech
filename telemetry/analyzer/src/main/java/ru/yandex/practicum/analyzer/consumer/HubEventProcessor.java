package ru.yandex.practicum.analyzer.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.service.hub.HubEventService;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class HubEventProcessor implements Runnable {

    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    private final KafkaConsumer<String, HubEventAvro> kafkaHubEventConsumer;
    private final String hubTopic;
    private final HubEventService hubEventService;

    public HubEventProcessor(KafkaConsumer<String, HubEventAvro> kafkaHubEventConsumer,
                             @Qualifier("hubTopic") String hubTopic,
                             HubEventService hubEventService) {
        this.kafkaHubEventConsumer = kafkaHubEventConsumer;
        this.hubTopic = hubTopic;
        this.hubEventService = hubEventService;
    }

    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaHubEventConsumer::wakeup));
        try {
            kafkaHubEventConsumer.subscribe(List.of(hubTopic));

            while (true) {
                ConsumerRecords<String, HubEventAvro> records = kafkaHubEventConsumer.poll(Duration.ofSeconds(4));
                int count = 0;

                for (ConsumerRecord<String, HubEventAvro> record : records) {
                    handleRecord(record);
                    manageOffsets(record, count, kafkaHubEventConsumer);
                    count++;
                }

                kafkaHubEventConsumer.commitAsync();
            }
        } catch (WakeupException e) {
            log.info("Kafka consumer wakeup received, shutting down...");
        } catch (Exception e) {
            log.error("Error during processing hub events", e);
        } finally {
            try {
                kafkaHubEventConsumer.commitSync(currentOffsets);
            } catch (Exception e) {
                log.warn("Error during final offset commit", e);
            }
            log.info("Closing hub consumer");
            kafkaHubEventConsumer.close();
        }
    }

    private static void manageOffsets(ConsumerRecord<String, HubEventAvro> record,
                                      int count,
                                      KafkaConsumer<String, HubEventAvro> consumer) {
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

    private void handleRecord(ConsumerRecord<String, HubEventAvro> record) {
        hubEventService.handle(record.value());
    }
}
