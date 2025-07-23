package ru.yandex.practicum.collector.service.sensors.mappers;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SensorEventMapperFactory {

    private final List<SensorEventMapper> mappers;

    public SpecificRecord mapToAvro(SensorEvent event) {
        return mappers.stream()
                .filter(m -> m.supports(event.getType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Unknown sensor type: %s", event.getType())
                ))
                .mapToAvro(event);
    }
}