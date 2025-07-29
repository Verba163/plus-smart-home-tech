package ru.yandex.practicum.analyzer.service.hub.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.model.Action;
import ru.yandex.practicum.analyzer.model.Condition;
import ru.yandex.practicum.analyzer.model.Scenario;
import ru.yandex.practicum.analyzer.repository.ActionRepository;
import ru.yandex.practicum.analyzer.repository.ConditionRepository;
import ru.yandex.practicum.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.analyzer.service.hub.config.ConditionValidationService;
import ru.yandex.practicum.analyzer.service.hub.config.SensorValidationService;
import ru.yandex.practicum.analyzer.service.hub.mappers.EnumMappers;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioAddedEventHandlers implements HubEventHandler<ScenarioAddedEventAvro> {

    private final ActionRepository actionRepository;
    private final ConditionRepository conditionRepository;
    private final ScenarioRepository scenarioRepository;
    private final EnumMappers enumMappers;
    private final SensorValidationService sensorValidationService;
    private final ConditionValidationService conditionValidationService;

    @Transactional
    @Override
    public void handle(String hubId, ScenarioAddedEventAvro event) {
        try {

            for (ScenarioConditionAvro condition : event.getConditions()) {
                sensorValidationService.validateSensor(hubId, condition.getSensorId());
            }
            for (DeviceActionAvro action : event.getActions()) {
                sensorValidationService.validateSensor(hubId, action.getSensorId());
            }

            Scenario scenario = Scenario.builder()
                    .name(event.getName())
                    .hubId(hubId)
                    .conditions(new HashMap<>())
                    .actions(new HashMap<>())
                    .build();

            List<Condition> conditionsToSave = new ArrayList<>();
            for (ScenarioConditionAvro conditionAvro : event.getConditions()) {
                Integer value = conditionValidationService.getConditionValue(conditionAvro);

                Condition condition = Condition.builder()
                        .type(enumMappers.ConditionTypeMapper(conditionAvro.getType()))
                        .operation(enumMappers.ConditionOperationMapper(conditionAvro.getOperation()))
                        .value(value)
                        .build();
                conditionsToSave.add(condition);
                scenario.getConditions().put(conditionAvro.getSensorId(), condition);
            }
            conditionRepository.saveAll(conditionsToSave);

            List<Action> actionsToSave = new ArrayList<>();
            for (DeviceActionAvro actionAvro : event.getActions()) {
                Action action = Action.builder()
                        .type(enumMappers.ActionTypeMapper(actionAvro.getType()))
                        .value(actionAvro.getValue())
                        .build();
                actionsToSave.add(action);
                scenario.getActions().put(actionAvro.getSensorId(), action);
            }
            actionRepository.saveAll(actionsToSave);

            scenarioRepository.save(scenario);
            log.info("Scenario added: {}", scenario);
        } catch (Exception e) {
            log.error("Failed to save scenario '{}' for hub ID {}: {}", event.getName(), hubId, e.getMessage(), e);
            throw e;
        }
    }
}
