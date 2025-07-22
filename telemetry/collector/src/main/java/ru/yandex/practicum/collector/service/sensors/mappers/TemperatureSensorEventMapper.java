package ru.yandex.practicum.collector.service.sensors.mappers;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.model.sensors.TemperatureSensorEvent;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

@Component
public class TemperatureSensorEventMapper implements SensorEventMapper {

    @Override
    public boolean supports(SensorType type) {
        return type == SensorType.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public SpecificRecord mapToAvro(SensorEvent event) {
        TemperatureSensorEvent tempEvent = (TemperatureSensorEvent) event;
        return TemperatureSensorAvro.newBuilder()
                .setTemperatureC(tempEvent.getTemperatureC())
                .setTemperatureF(tempEvent.getTemperatureF())
                .build();
    }
}