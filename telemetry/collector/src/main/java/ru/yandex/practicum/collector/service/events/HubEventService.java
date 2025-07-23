package ru.yandex.practicum.collector.service.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.service.events.mappers.HubEventMapperFactory;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class HubEventService {

    private final Producer<String, SpecificRecordBase> kafkaProducer;
    private final HubEventMapperFactory mapperFactory;

    @Value("${topic.hub-events}")
    private String hubEventsTopic;

    public void processEvent(HubEvent hubEvent) {
        try {
            SpecificRecordBase payload = mapperFactory.mapToAvro(hubEvent);

            HubEventAvro avro = HubEventAvro.newBuilder()
                    .setHubId(hubEvent.getHubId())
                    .setTimestamp(hubEvent.getTimestamp() != null ? hubEvent.getTimestamp() : Instant.now())
                    .setPayload(payload)
                    .build();

            log.info("Sending HubEvent to Kafka with payload: {}", payload.getClass().getSimpleName());
            kafkaProducer.send(new ProducerRecord<>(hubEventsTopic, avro.getHubId(), avro));
        } catch (Exception e) {
            log.error("Error processing hub event: {}", hubEvent.getHubId(), e);
        }
    }
}