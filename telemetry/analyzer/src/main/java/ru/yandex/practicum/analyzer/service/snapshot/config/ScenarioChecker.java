package ru.yandex.practicum.analyzer.service.snapshot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.model.Condition;
import ru.yandex.practicum.analyzer.model.Scenario;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioChecker {

    private final ConditionChecker conditionChecker;
    private final ActionExecutor actionExecutor;

    public void executeScenario(Scenario scenario, Map<String, SensorStateAvro> sensorsState, Instant timestamp) {
        try {
            log.info("Evaluating scenario '{}' for hub {}", scenario.getName(), scenario.getHubId());

            boolean allConditionsMet = scenario.getConditions().entrySet().stream()
                    .allMatch(entry -> {
                        String sensorId = entry.getKey();
                        Condition condition = entry.getValue();
                        SensorStateAvro state = sensorsState.get(sensorId);
                        if (state == null) {
                            log.warn("Sensor {} not found in current state for scenario '{}'", sensorId, scenario.getName());
                            return false;
                        }
                        try {
                            return conditionChecker.checkCondition(condition, state.getData());
                        } catch (Exception e) {
                            log.error("Error checking condition for sensor {}: {}", sensorId, e.getMessage(), e);
                            return false;
                        }
                    });

            if (allConditionsMet) {
                scenario.getActions().forEach((sensorId, action) -> {
                    try {
                        actionExecutor.executeAction(sensorId, action, scenario.getHubId(), scenario.getName(), timestamp);
                    } catch (Exception e) {
                        log.error("Error executing action for sensor {}: {}", sensorId, e.getMessage(), e);
                    }
                });
                log.info("Scenario '{}' executed for hub {}", scenario.getName(), scenario.getHubId());
            }
        } catch (Exception e) {
            log.error("Unexpected error while evaluating scenario '{}': {}", scenario.getName(), e.getMessage(), e);
        }
    }
}