package ru.yandex.practicum.analyzer.service.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.service.hub.handlers.DeviceAddedEventHandler;
import ru.yandex.practicum.analyzer.service.hub.handlers.DeviceRemovedEventHandler;
import ru.yandex.practicum.analyzer.service.hub.handlers.ScenarioAddedEventHandlers;
import ru.yandex.practicum.analyzer.service.hub.handlers.ScenarioRemovedEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class HubEventService {

    DeviceAddedEventHandler deviceAddedEventHandler;
    DeviceRemovedEventHandler deviceRemovedEventHandler;
    ScenarioAddedEventHandlers scenarioAddedEventHandlers;
    ScenarioRemovedEventHandler scenarioRemovedEventHandler;

    @Transactional
    public void handle(HubEventAvro hubEvent) {
        try {
            Object payload = hubEvent.getPayload();
            switch (payload) {
                case DeviceAddedEventAvro event -> deviceAddedEventHandler.handle(hubEvent.getHubId(), event);
                case DeviceRemovedEventAvro event -> deviceRemovedEventHandler.handle(hubEvent.getHubId(), event);
                case ScenarioAddedEventAvro event -> scenarioAddedEventHandlers.handle(hubEvent.getHubId(), event);
                case ScenarioRemovedEventAvro event -> scenarioRemovedEventHandler.handle(hubEvent.getHubId(), event);
                default -> log.info("Unknown event type: {}", payload.getClass().getName());
            }
        } catch (Exception e) {
            log.error("Unexpected error while processing event {}: {}", hubEvent, e.getMessage(), e);
        }
    }
}