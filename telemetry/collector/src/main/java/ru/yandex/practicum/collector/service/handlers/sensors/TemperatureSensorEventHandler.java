package ru.yandex.practicum.collector.service.handlers.sensors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.model.sensors.TemperatureSensorEvent;
import ru.yandex.practicum.collector.service.sensors.SensorsEventService;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorProto;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class TemperatureSensorEventHandler implements SensorEventHandler {

    private final SensorsEventService sensorsEventService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {

        SensorEvent sensorEvent;

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

        sensorEvent = tempEvent;

        sensorsEventService.processEvent(tempEvent);
    }
}

