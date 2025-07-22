package ru.yandex.practicum.collector.service.sensors.mappers;

import org.apache.avro.specific.SpecificRecord;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;

public interface SensorEventMapper {
    boolean supports(SensorType type);

    SpecificRecord mapToAvro(SensorEvent event);
}