package ru.yandex.practicum.analyzer.service.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.service.hub.handlers.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class HubEventService {

    private final Map<Class<?>, HubEventHandler<?>> handlers;

    @Transactional
    @SuppressWarnings("unchecked")
    public void handle(HubEventAvro hubEvent) {
        try {
            Object payload = hubEvent.getPayload();
            HubEventHandler<Object> handler = (HubEventHandler<Object>) handlers.get(payload.getClass());
            if (handler != null) {
                handler.handle(hubEvent.getHubId(), payload);
            } else {
                log.info("Unknown event type: {}", payload.getClass().getName());
            }
        } catch (Exception e) {
            log.error("Unexpected error while processing event {}: {}", hubEvent, e.getMessage(), e);
        }
    }
}






