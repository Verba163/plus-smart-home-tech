package ru.yandex.practicum.collector.service.handlers.sensors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.MotionSensorEvent;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.service.sensors.SensorsEventService;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class MotionSensorEventHandler implements SensorEventHandler {

    private final SensorsEventService sensorsEventService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {

        SensorEvent sensorEvent;

        MotionSensorProto motionProto = sensorEventProto.getMotionSensorEvent();

        MotionSensorEvent motionEvent = new MotionSensorEvent();
        motionEvent.setId(sensorEventProto.getId());
        motionEvent.setHubId(sensorEventProto.getHubId());

        Instant timestamp = Instant.ofEpochSecond(
                sensorEventProto.getTimestamp().getSeconds(),
                sensorEventProto.getTimestamp().getNanos()
        );
        motionEvent.setTimestamp(timestamp);

        motionEvent.setLinkQuality(motionProto.getLinkQuality());
        motionEvent.setMotion(motionProto.getMotion());
        motionEvent.setVoltage(motionProto.getVoltage());

        sensorEvent = motionEvent;

        sensorsEventService.processEvent(motionEvent);
    }
}

