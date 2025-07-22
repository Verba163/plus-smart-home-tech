package ru.yandex.practicum.collector.model.sensors;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.model.sensors.enums.SensorType;

@Getter
@Setter
@ToString(callSuper = true)
public class LightSensorEvent extends SensorEvent {

    int linkQuality;
    int luminosity;

    @Override
    public SensorType getType() {
        return SensorType.LIGHT_SENSOR_EVENT;
    }
}
