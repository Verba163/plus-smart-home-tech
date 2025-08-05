package ru.yandex.practicum.analyzer.service.hub.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.model.Sensor;
import ru.yandex.practicum.analyzer.repository.SensorRepository;

@Component
@RequiredArgsConstructor
public class SensorValidationService {

    private final SensorRepository sensorRepository;

    public void validateSensor(String hubId, String sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found: " + sensorId));
        if (!hubId.equals(sensor.getHubId())) {
            throw new IllegalArgumentException(
                    String.format("Hub ID mismatch for sensor %s: expected %s but found %s",
                            sensorId, hubId, sensor.getHubId()));
        }
    }
}
