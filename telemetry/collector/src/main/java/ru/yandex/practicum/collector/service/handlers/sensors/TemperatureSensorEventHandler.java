package ru.yandex.practicum.collector.service.handlers.sensors;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.TemperatureSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorProto;

import java.time.Instant;

@Component
public class TemperatureSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {
        TemperatureSensorProto tempProto = sensorEventProto.getTemperatureSensorEvent();

        TemperatureSensorEvent tempEvent = new TemperatureSensorEvent();
        tempEvent.setId(sensorEventProto.getId());
        tempEvent.setHubId(sensorEventProto.getHubId());

        Instant timestamp = Instant.ofEpochSecond(
                sensorEventProto.getTimestamp().getSeconds(),
                sensorEventProto.getTimestamp().getNanos()
        );
        tempEvent.setTimestamp(timestamp);

        tempEvent.setTemperatureC(tempProto.getTemperatureC());
        tempEvent.setTemperatureF(tempProto.getTemperatureF());

    }
}

