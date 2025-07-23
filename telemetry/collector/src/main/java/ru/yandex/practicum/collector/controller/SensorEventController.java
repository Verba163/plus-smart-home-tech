package ru.yandex.practicum.collector.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.model.sensors.SensorEvent;
import ru.yandex.practicum.collector.service.events.HubEventService;
import ru.yandex.practicum.collector.service.sensors.SensorsEventService;

import static ru.yandex.practicum.collector.constants.Constants.BASE_PATH;
import static ru.yandex.practicum.collector.constants.Constants.SENSORS_PATH;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class SensorEventController {

    private final HubEventService hubEventService;
    private final SensorsEventService sensorsEventService;

    @PostMapping(SENSORS_PATH)
    public ResponseEntity<String> collectSensorEvent(@RequestBody SensorEvent sensorEvent) {
        try {
            sensorsEventService.processEvent(sensorEvent);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error processing sensor event at {}/{}: {}", BASE_PATH, SENSORS_PATH, sensorEvent, e);
            return ResponseEntity.internalServerError()
                    .body(String.format("Error: %s: %s", e.getClass().getSimpleName(), e.getMessage()));
        }
    }
}