package ru.yandex.practicum.collector.model.sensors;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;

@Getter
@Setter
@ToString(callSuper = true)
public class TemperatureSensorEvent extends SensorEvent {

    @NotNull
    int temperatureC;
    @NotNull
    int temperatureF;

    @Override
    public SensorType getType() {
        return SensorType.TEMPERATURE_SENSOR_EVENT;
    }
}