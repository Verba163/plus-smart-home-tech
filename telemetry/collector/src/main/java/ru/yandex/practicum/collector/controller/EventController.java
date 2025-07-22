package ru.yandex.practicum.collector.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.service.sensors.SensorsEventService;
import ru.yandex.practicum.collector.service.events.HubEventService;

import static ru.yandex.practicum.collector.constants.Constants.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(BASE_PATH)
public class EventController {

    private final HubEventService hubEventService;
    private final SensorsEventService sensorsEventService;

    @PostMapping(BASE_PATH + SENSORS_PATH)
    public ResponseEntity<String> collectSensorEvent(@RequestBody SensorEvent sensorEvent) {
        try {
            sensorsEventService.processEvent(sensorEvent);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error processing sensor event at {}/{}: {}", BASE_PATH, SENSORS_PATH, sensorEvent, e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @PostMapping(BASE_PATH + HUBS_PATH)
    public ResponseEntity<String> collectHubEvent(@RequestBody HubEvent hubEvent) {
        try {
            hubEventService.processEvent(hubEvent);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error processing hub event at {}/{}: {}", BASE_PATH, HUBS_PATH, hubEvent, e);
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}