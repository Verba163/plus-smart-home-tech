package ru.yandex.practicum.analyzer.service.snapshot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.model.Sensor;
import ru.yandex.practicum.analyzer.repository.SensorRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorValidator {

    private final SensorRepository sensorRepository;

    public void validateSensor(String hubId, String sensorId) {
        try {
            Sensor sensor = sensorRepository.findById(sensorId)
                    .orElseThrow(() -> new IllegalArgumentException("Sensor not found: " + sensorId));
            if (!hubId.equals(sensor.getHubId())) {
                throw new IllegalArgumentException(String.format(
                        "Hub ID mismatch for sensor %s: expected %s, found %s",
                        sensorId, hubId, sensor.getHubId()));
            }
        } catch (Exception e) {
            log.error("Error validating sensor existence in database: {}", e.getMessage(), e);
            throw e;
        }
    }
}

