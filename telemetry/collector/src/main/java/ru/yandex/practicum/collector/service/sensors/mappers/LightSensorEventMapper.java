package ru.yandex.practicum.collector.service.sensors.mappers;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.LightSensorEvent;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;

@Component
public class LightSensorEventMapper implements SensorEventMapper {

    @Override
    public boolean supports(SensorType type) {
        return type == SensorType.LIGHT_SENSOR_EVENT;
    }

    @Override
    public SpecificRecord mapToAvro(SensorEvent event) {
        LightSensorEvent lightEvent = (LightSensorEvent) event;
        return LightSensorAvro.newBuilder()
                .setLinkQuality(lightEvent.getLinkQuality())
                .setLuminosity(lightEvent.getLuminosity())
                .build();
    }
}