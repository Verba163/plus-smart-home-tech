package ru.yandex.practicum.collector.model.sensors;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;


@Getter
@Setter
@ToString(callSuper = true)
public class ClimateSensorEvent extends SensorEvent {

    @NotNull
    int temperatureC;
    @NotNull
    int humidity;
    @NotNull
    int co2Level;

    @Override
    public SensorType getType() {
        return SensorType.CLIMATE_SENSOR_EVENT;
    }
}
