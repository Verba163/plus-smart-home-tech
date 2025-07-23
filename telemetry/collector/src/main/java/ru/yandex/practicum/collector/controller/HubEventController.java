package ru.yandex.practicum.collector.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.service.events.HubEventService;

import static ru.yandex.practicum.collector.constants.Constants.BASE_PATH;
import static ru.yandex.practicum.collector.constants.Constants.HUBS_PATH;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class HubEventController {

    private final HubEventService hubEventService;

    @PostMapping(HUBS_PATH)
    public ResponseEntity<String> collectHubEvent(@RequestBody HubEvent hubEvent) {
        try {
            hubEventService.processEvent(hubEvent);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error processing hub event at {}/{}: {}", BASE_PATH, HUBS_PATH, hubEvent, e);
            return ResponseEntity.internalServerError()
                    .body(String.format("Error: %s: %s", e.getClass().getSimpleName(), e.getMessage()));
        }
    }
}
