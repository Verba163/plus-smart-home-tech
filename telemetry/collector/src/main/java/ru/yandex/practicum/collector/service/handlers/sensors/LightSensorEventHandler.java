package ru.yandex.practicum.collector.service.handlers.sensors;

import ru.yandex.practicum.collector.model.sensors.LightSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

public class LightSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {

        LightSensorProto lightProto = sensorEventProto.getLightSensorEvent();

        LightSensorEvent lightEvent = new LightSensorEvent();
        lightEvent.setId(sensorEventProto.getId());
        lightEvent.setHubId(sensorEventProto.getHubId());

        Instant timestamp = Instant.ofEpochSecond(
                sensorEventProto.getTimestamp().getSeconds(),
                sensorEventProto.getTimestamp().getNanos()
        );
        lightEvent.setTimestamp(timestamp);

        lightEvent.setLinkQuality(lightProto.getLinkQuality());
        lightEvent.setLuminosity(lightProto.getLuminosity());

    }
}
