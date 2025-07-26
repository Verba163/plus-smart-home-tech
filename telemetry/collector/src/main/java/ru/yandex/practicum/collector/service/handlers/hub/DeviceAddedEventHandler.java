package ru.yandex.practicum.collector.service.handlers.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.hub.enums.DeviceType;
import ru.yandex.practicum.collector.service.events.HubEventService;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DeviceAddedEventHandler implements HubEventHandler {

    private final HubEventService hubEventService;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }

    @Override
    public void handle(HubEventProto hubEventProto) {

        HubEvent hubEvent;

        DeviceAddedEventProto deviceAddedEventProto = hubEventProto.getDeviceAdded();
        DeviceAddedEvent deviceAddedEvent = new DeviceAddedEvent();

        deviceAddedEvent.setId(deviceAddedEventProto.getId());

        Instant timestamp = Instant.ofEpochSecond(
                hubEventProto.getTimestamp().getSeconds(),
                hubEventProto.getTimestamp().getNanos()
        );

        deviceAddedEvent.setTimestamp(timestamp);
        deviceAddedEvent.setDeviceType(DeviceType.valueOf(deviceAddedEventProto.getType().name()));

        hubEvent = deviceAddedEvent;
        hubEvent.setHubId(hubEventProto.getHubId());
        hubEventService.processEvent(deviceAddedEvent);

    }
}