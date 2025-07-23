package ru.yandex.practicum.collector.model.sensors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemperatureSensorEvent extends SensorEvent {

    int temperatureC;
    int temperatureF;

    @Override
    public SensorType getType() {
        return SensorType.TEMPERATURE_SENSOR_EVENT;
    }
}