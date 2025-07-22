package ru.yandex.practicum.collector.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.service.sensors.mappers.SensorEventMapperFactory;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Instant;

/**
 * Service for processing sensor events and sending them to Kafka.
 */
@Slf4j
@Service
public class SensorsEventService {

    private final KafkaProducer<String, SpecificRecord> kafkaProducer;
    private final SensorEventMapperFactory mapperFactory;

    @Value("${topic.sensor-events}")
    private String sensorEventsTopic;

    public SensorsEventService(KafkaProducer<String, SpecificRecord> kafkaProducer,
                               SensorEventMapperFactory mapperFactory) {
        this.kafkaProducer = kafkaProducer;
        this.mapperFactory = mapperFactory;
    }

    public void processEvent(SensorEvent sensorEvent) {
        try {
            SensorEventAvro avro = mapToAvro(sensorEvent);
            log.info("SensorEvent sent to Kafka with payload: {}", avro.getPayload().getClass().getSimpleName());
            kafkaProducer.send(new ProducerRecord<>(sensorEventsTopic, avro.getId(), avro));
        } catch (Exception e) {
            log.error("Error processing sensor event: {}", sensorEvent.getId(), e);
        }
    }

    private SensorEventAvro mapToAvro(SensorEvent sensorEvent) {
        long timestamp = (sensorEvent.getTimestamp() != null)
                ? sensorEvent.getTimestamp().toEpochMilli()
                : Instant.now().toEpochMilli();

        SpecificRecord payload = mapperFactory.mapToAvro(sensorEvent);

        return SensorEventAvro.newBuilder()
                .setId(sensorEvent.getId())
                .setHubId(sensorEvent.getHubId())
                .setTimestamp(Instant.ofEpochMilli(timestamp))
                .setPayload(payload)
                .build();
    }
}
