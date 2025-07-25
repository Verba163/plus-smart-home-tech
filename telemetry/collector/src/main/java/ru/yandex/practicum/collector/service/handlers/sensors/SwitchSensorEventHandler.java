package ru.yandex.practicum.collector.service.handlers.sensors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.model.sensors.SwitchSensorEvent;
import ru.yandex.practicum.collector.service.sensors.SensorsEventService;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorProto;

import java.time.Instant;

@Component
@Slf4j
public class SwitchSensorEventHandler implements SensorEventHandler {

    SensorsEventService sensorsEventService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {
        SensorEvent sensorEvent;

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

        sensorEvent = switchEvent;

        sensorsEventService.processEvent(switchEvent);
    }
}
