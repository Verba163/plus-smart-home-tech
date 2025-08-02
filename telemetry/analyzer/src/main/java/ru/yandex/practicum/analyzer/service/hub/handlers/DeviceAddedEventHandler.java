package ru.yandex.practicum.analyzer.service.hub.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.model.Sensor;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceAddedEventHandler implements HubEventHandler<DeviceAddedEventAvro> {

    private final SensorRepository sensorRepository;

    @Override
    @Transactional
    public void handle(String hubId, DeviceAddedEventAvro event) {
        if (sensorRepository.existsById(event.getId())) {
            log.info("Sensor with ID {} already exists", event.getId());
            return;
        }
        Sensor sensor = Sensor.builder()
                .id(event.getId())
                .hubId(hubId)
                .build();
        sensorRepository.save(sensor);
        log.info("Added sensor: {}", sensor);
    }
}
