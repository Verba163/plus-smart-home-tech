package ru.yandex.practicum.collector.service.handlers.sensors;

import ru.yandex.practicum.collector.model.sensors.ClimateSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

public class ClimateSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {

        ClimateSensorProto climateProto = sensorEventProto.getClimateSensorEvent();

        ClimateSensorEvent climateEvent = new ClimateSensorEvent();
        climateEvent.setId(sensorEventProto.getId());
        climateEvent.setHubId(sensorEventProto.getHubId());

        Instant timestamp = Instant.ofEpochSecond(
                sensorEventProto.getTimestamp().getSeconds(),
                sensorEventProto.getTimestamp().getNanos()
        );
        climateEvent.setTimestamp(timestamp);

        climateEvent.setTemperatureC(climateProto.getTemperatureC());
        climateEvent.setHumidity(climateProto.getHumidity());
        climateEvent.setCo2Level(climateProto.getCo2Level());

    }
}
