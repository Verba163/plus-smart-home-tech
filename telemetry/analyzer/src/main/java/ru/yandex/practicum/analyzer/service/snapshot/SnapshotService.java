package ru.yandex.practicum.analyzer.service.snapshot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.analyzer.service.snapshot.config.ScenarioChecker;
import ru.yandex.practicum.analyzer.service.snapshot.config.SensorValidator;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class SnapshotService {

    private final SensorValidator sensorValidator;
    private final ScenarioRepository scenarioRepository;
    private final ScenarioChecker scenarioChecker;

    @Transactional
    public void handleSnapshot(SensorsSnapshotAvro snapshot) {
        String hubId = snapshot.getHubId();
        Map<String, SensorStateAvro> sensorsState = snapshot.getSensorsState();

        sensorsState.keySet().forEach(sensorId -> sensorValidator.validateSensor(hubId, sensorId));

        scenarioRepository.findByHubId(hubId).forEach(scenario ->
                scenarioChecker.executeScenario(scenario, sensorsState, snapshot.getTimestamp())
        );

        log.info("Processed snapshot for hub {} with {} sensors", hubId, sensorsState.size());
    }
}
