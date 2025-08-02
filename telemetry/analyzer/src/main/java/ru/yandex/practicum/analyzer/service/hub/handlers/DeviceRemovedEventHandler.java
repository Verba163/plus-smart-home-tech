package ru.yandex.practicum.analyzer.service.hub.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceRemovedEventHandler implements HubEventHandler<DeviceRemovedEventAvro> {

    private final SensorRepository sensorRepository;

    @Override
    @Transactional
    public void handle(String hubId, DeviceRemovedEventAvro event) {
        try {
            String sensorId = event.getId();
            if (!sensorRepository.existsById(sensorId)) {
                log.warn("Sensor with ID {} does not exist and cannot be removed", sensorId);
                return;
            }
            sensorRepository.deleteById(sensorId);
            log.info("Removed sensor: {}", sensorId);
        } catch (Exception e) {
            log.error("Error while removing sensor with ID {}: {}", event.getId(), e.getMessage(), e);
        }
    }
}

