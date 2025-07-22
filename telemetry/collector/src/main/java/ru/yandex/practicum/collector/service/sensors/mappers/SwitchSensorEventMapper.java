package ru.yandex.practicum.collector.service.sensors.mappers;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.model.sensors.SwitchSensorEvent;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@Component
public class SwitchSensorEventMapper implements SensorEventMapper {

    @Override
    public boolean supports(SensorType type) {
        return type == SensorType.SWITCH_SENSOR_EVENT;
    }

    @Override
    public SpecificRecord mapToAvro(SensorEvent event) {
        SwitchSensorEvent switchEvent = (SwitchSensorEvent) event;
        return SwitchSensorAvro.newBuilder()
                .setState(switchEvent.isState())
                .build();
    }
}
