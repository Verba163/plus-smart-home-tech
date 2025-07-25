package ru.yandex.practicum.collector.service.handlers.sensors;

import ru.yandex.practicum.collector.model.sensors.MotionSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

public class MotionSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {

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

    }
}

