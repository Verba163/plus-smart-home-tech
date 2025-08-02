package ru.yandex.practicum.collector.service.handlers.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.hub.DeviceRemovedEvent;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.service.events.HubEventService;
import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DeviceRemovedEventHandler implements HubEventHandler {

    private final HubEventService hubEventService;

    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }

    @Override
    public void handle(HubEventProto hubEventProto) {
        HubEvent hubEvent;

        DeviceRemovedEventProto deviceRemoved = hubEventProto.getDeviceRemoved();
        DeviceRemovedEvent deviceRemovedEvent = new DeviceRemovedEvent();
        deviceRemovedEvent.setId(deviceRemoved.getId());

        Instant timestamp = Instant.ofEpochSecond(
                hubEventProto.getTimestamp().getSeconds(),
                hubEventProto.getTimestamp().getNanos()
        );

        deviceRemovedEvent.setTimestamp(timestamp);

        hubEvent = deviceRemovedEvent;

        hubEventService.processEvent(deviceRemovedEvent);
    }
}
