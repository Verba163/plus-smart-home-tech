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
public class ClimateSensorEvent extends SensorEvent {

    int temperatureC;
    int humidity;
    int co2Level;

    @Override
    public SensorType getType() {
        return SensorType.CLIMATE_SENSOR_EVENT;
    }
}
