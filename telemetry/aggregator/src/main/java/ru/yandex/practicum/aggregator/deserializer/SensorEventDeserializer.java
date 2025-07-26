package ru.yandex.practicum.aggregator.deserializer;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
public class SensorEventDeserializer extends BaseAvroDeserializer<SensorEventAvro> {

    public SensorEventDeserializer() {
        super(SensorEventAvro.class, SensorEventAvro.getClassSchema());
    }

    @Override
    public SensorEventAvro deserialize(String topic, byte[] data) {
        return super.deserialize(topic, data);
    }
}