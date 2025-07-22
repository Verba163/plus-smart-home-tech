package ru.yandex.practicum.collector.model.sensors;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;

@Getter
@Setter
@ToString(callSuper = true)
public class MotionSensorEvent extends SensorEvent {
    @NotNull
    int linkQuality;
    @NotNull
    boolean motion;
    @NotNull
    int voltage;

    @Override
    public SensorType getType() {
        return SensorType.MOTION_SENSOR_EVENT;
    }
}