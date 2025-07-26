package ru.yandex.practicum.collector.service.handlers.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.hub.DeviceAction;
import ru.yandex.practicum.collector.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.collector.model.hub.ScenarioCondition;
import ru.yandex.practicum.collector.model.hub.enums.ActionType;
import ru.yandex.practicum.collector.model.hub.enums.ConditionOperation;
import ru.yandex.practicum.collector.model.hub.enums.ConditionType;
import ru.yandex.practicum.collector.service.events.HubEventService;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScenarioAddedEventHandler implements HubEventHandler {

    private final HubEventService hubEventService;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEventProto hubEventProto) {

        ScenarioAddedEventProto scenarioAddedEventProto = hubEventProto.getScenarioAdded();

        ScenarioAddedEvent scenarioAddedEvent = new ScenarioAddedEvent();

        scenarioAddedEvent.setName(scenarioAddedEventProto.getName());

        Instant timestamp = Instant.ofEpochSecond(
                hubEventProto.getTimestamp().getSeconds(),
                hubEventProto.getTimestamp().getNanos()
        );

        List<ScenarioCondition> scenarioConditions = scenarioAddedEventProto.getConditionList().stream()
                .map(conditionProto -> {
                    ScenarioCondition scenarioCondition = new ScenarioCondition();
                    scenarioCondition.setSensorId(conditionProto.getSensorId());
                    scenarioCondition.setType(ConditionType.valueOf(conditionProto.getType().name()));
                    scenarioCondition.setOperation(ConditionOperation.valueOf(conditionProto.getOperation().name()));

                    switch (conditionProto.getValueCase()) {
                        case INT_VALUE -> scenarioCondition.setValue(conditionProto.getIntValue());
                        case BOOL_VALUE -> scenarioCondition.setValue(conditionProto.getBoolValue() ? 1 : 0);
                    }
                    return scenarioCondition;
                })
                .collect(Collectors.toList());

        List<DeviceAction> deviceActions = scenarioAddedEventProto.getActionList().stream()
                .map(actionProto -> {
                    DeviceAction deviceAction = new DeviceAction();
                    deviceAction.setSensorId(actionProto.getSensorId());
                    deviceAction.setType(ActionType.valueOf(actionProto.getType().name()));
                    deviceAction.setValue(actionProto.hasValue() ? actionProto.getValue() : null);
                    return deviceAction;
                })
                .collect(Collectors.toList());

        scenarioAddedEvent.setConditions(scenarioConditions);
        scenarioAddedEvent.setActions(deviceActions);
        scenarioAddedEvent.setHubId(hubEventProto.getHubId());
        scenarioAddedEvent.setTimestamp(timestamp);

        hubEventService.processEvent(scenarioAddedEvent);
    }
}

