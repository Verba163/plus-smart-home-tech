package ru.yandex.practicum.aggregator.deserializer;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class HubEventDeserializer extends BaseAvroDeserializer<HubEventAvro> {

    public HubEventDeserializer() {
        super(HubEventAvro.class, HubEventAvro.getClassSchema());
    }
}