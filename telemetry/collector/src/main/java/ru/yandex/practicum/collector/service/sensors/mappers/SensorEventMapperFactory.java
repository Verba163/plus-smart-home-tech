package ru.yandex.practicum.collector.service.sensors.mappers;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;

import java.util.List;

@Service
public class SensorEventMapperFactory {

    private final List<SensorEventMapper> mappers;

    @Autowired
    public SensorEventMapperFactory(List<SensorEventMapper> mappers) {
        this.mappers = mappers;
    }

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