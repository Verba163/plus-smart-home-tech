package ru.yandex.practicum.aggregator.deserializer;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;


@Component
public class SensorsSnapshotDeserializer extends BaseAvroDeserializer<SensorsSnapshotAvro> {

    public SensorsSnapshotDeserializer() {
        super(SensorsSnapshotAvro.class, SensorsSnapshotAvro.getClassSchema());
    }
}