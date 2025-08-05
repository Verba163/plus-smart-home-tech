package ru.yandex.practicum.collector.service.handlers.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.collector.service.events.HubEventService;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventHandler implements HubEventHandler {

    private final HubEventService hubEventService;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }

    @Override
    public void handle(HubEventProto hubEventProto) {

        HubEvent event;

        ScenarioRemovedEventProto scenarioRemovedEventProto = hubEventProto.getScenarioRemoved();
        ScenarioRemovedEvent scenarioRemovedEvent = new ScenarioRemovedEvent();

        Instant timestamp = Instant.ofEpochSecond(
                hubEventProto.getTimestamp().getSeconds(),
                hubEventProto.getTimestamp().getNanos()
        );

        scenarioRemovedEvent.setTimestamp(timestamp);
        scenarioRemovedEvent.setName(scenarioRemovedEventProto.getName());
        event = scenarioRemovedEvent;

        hubEventService.processEvent(scenarioRemovedEvent);

    }
}
