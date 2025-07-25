package ru.yandex.practicum.collector.service.handlers.sensors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.collector.model.sensors.LightSensorEvent;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.service.sensors.SensorsEventService;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Instant;

@Component
@Slf4j
public class LightSensorEventHandler implements SensorEventHandler {

    SensorsEventService sensorsEventService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto sensorEventProto) {

        SensorEvent sensorEvent;

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

        sensorEvent = lightEvent;

        sensorsEventService.processEvent(lightEvent);
    }
}
