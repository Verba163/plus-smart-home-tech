package ru.yandex.practicum.collector.service.handlers.sensors;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

public interface SensorEventHandler {

    SensorEventProto.PayloadCase getMessageType();

    void handle(SensorEventProto sensorEventProto);
}
