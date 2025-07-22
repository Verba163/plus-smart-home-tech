package ru.yandex.practicum.collector.model.sensors;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;


@Getter
@Setter
@ToString(callSuper = true)
public class SwitchSensorEvent extends SensorEvent {

    @NotNull
    boolean state;

    @Override
    public SensorType getType() {
        return SensorType.SWITCH_SENSOR_EVENT;
    }
}