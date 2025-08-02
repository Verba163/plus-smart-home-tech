package ru.yandex.practicum.analyzer.deserializer;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public class HubEventDeserializer extends BaseAvroDeserializer<HubEventAvro> {

    public HubEventDeserializer() {
        super(HubEventAvro.class);
    }

    @Override
    protected org.apache.avro.Schema getSchema() {
        return HubEventAvro.getClassSchema();
    }
}