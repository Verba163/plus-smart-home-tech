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
            boolean allConditionsMet = scenario.getConditions().entrySet().stream()
                    .allMatch(entry -> {
                        String sensorId = entry.getKey();
                        Condition condition = entry.getValue();
                        SensorStateAvro state = sensorsState.get(sensorId);
                        return state != null && conditionChecker.checkCondition(condition, state.getData());
                    });

            if (allConditionsMet) {
                scenario.getActions().forEach((sensorId, action) ->
                        actionExecutor.executeAction(sensorId, action, scenario.getHubId(), scenario.getName(), timestamp)
                );
                log.info("Scenario '{}' executed for hub {}", scenario.getName(), scenario.getHubId());
            }
        } catch (Exception e) {
            log.error("Error while evaluating scenario conditions: {}", e.getMessage(), e);
        }
    }
}
