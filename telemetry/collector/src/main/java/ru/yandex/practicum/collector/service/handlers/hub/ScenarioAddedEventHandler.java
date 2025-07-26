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
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;

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

        ScenarioAddedEvent scenarioAddedEvent = new ScenarioAddedEvent();

        scenarioAddedEvent.setName(hubEventProto.getScenarioAdded().getName());

        Instant timestamp = Instant.ofEpochSecond(
                hubEventProto.getTimestamp().getSeconds(),
                hubEventProto.getTimestamp().getNanos()
        );
        scenarioAddedEvent.setTimestamp(timestamp);

        List<ScenarioCondition> conditions = hubEventProto.getScenarioAdded().getConditionList().stream()
                .map(this::mapCondition)
                .collect(Collectors.toList());
        scenarioAddedEvent.setConditions(conditions);

        List<DeviceAction> actions = hubEventProto.getScenarioAdded().getActionList().stream()
                .map(this::mapAction)
                .collect(Collectors.toList());
        scenarioAddedEvent.setActions(actions);

        scenarioAddedEvent.setHubId(hubEventProto.getHubId());

        hubEventService.processEvent(scenarioAddedEvent);
    }

    private ScenarioCondition mapCondition(ScenarioConditionProto proto) {
        ScenarioCondition condition = new ScenarioCondition();
        condition.setSensorId(proto.getSensorId());
        condition.setType(ConditionType.valueOf(proto.getType().name()));
        condition.setOperation(ConditionOperation.valueOf(proto.getOperation().name()));

        switch (proto.getValueCase()) {
            case INT_VALUE:
                condition.setValue(proto.getIntValue());
                break;
            case BOOL_VALUE:
                condition.setValue(proto.getBoolValue() ? 1 : 0);
                break;
            case VALUE_NOT_SET:
            default:
                condition.setValue(null);
                break;
        }
        return condition;
    }

    private DeviceAction mapAction(DeviceActionProto deviceActionProto) {
        DeviceAction action = new DeviceAction();
        action.setSensorId(deviceActionProto.getSensorId());
        action.setType(ActionType.valueOf(deviceActionProto.getType().name()));
        action.setValue(deviceActionProto.hasValue() ? deviceActionProto.getValue() : null);
        return action;
    }
}