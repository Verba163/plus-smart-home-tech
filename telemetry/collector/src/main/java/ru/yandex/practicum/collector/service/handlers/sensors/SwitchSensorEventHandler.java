package ru.yandex.practicum.collector.service.handlers.sensors;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.SwitchSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorProto;

import java.time.Instant;

@Component
public class SwitchSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {

        SwitchSensorProto switchProto = sensorEventProto.getSwitchSensorEvent();

        SwitchSensorEvent switchEvent = new SwitchSensorEvent();

        switchEvent.setState(switchProto.getState());
        switchEvent.setId(sensorEventProto.getId());
        switchEvent.setHubId(sensorEventProto.getHubId());
        Instant timestamp = Instant.ofEpochSecond(
                sensorEventProto.getTimestamp().getSeconds(),
                sensorEventProto.getTimestamp().getNanos()
        );
        switchEvent.setTimestamp(timestamp);
    }
}
