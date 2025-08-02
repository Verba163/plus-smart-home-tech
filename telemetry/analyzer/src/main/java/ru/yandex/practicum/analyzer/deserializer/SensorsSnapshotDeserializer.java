package ru.yandex.practicum.analyzer.deserializer;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;


public class SensorsSnapshotDeserializer extends BaseAvroDeserializer<SensorsSnapshotAvro> {

    public SensorsSnapshotDeserializer() {
        super(SensorsSnapshotAvro.class);
    }

    @Override
    protected org.apache.avro.Schema getSchema() {
        return SensorsSnapshotAvro.getClassSchema();
    }
}