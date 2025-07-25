package ru.yandex.practicum.collector.service.handlers.sensors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.ClimateSensorEvent;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.service.sensors.SensorsEventService;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClimateSensorEventHandler implements SensorEventHandler {

    SensorsEventService sensorsEventService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {
        SensorEvent sensorEvent;

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

        sensorEvent = climateEvent;

        sensorsEventService.processEvent(sensorEvent);
    }
}
