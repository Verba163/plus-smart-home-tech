package ru.yandex.practicum.collector.service.handlers.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.hub.DeviceAction;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.collector.model.hub.ScenarioCondition;
import ru.yandex.practicum.collector.model.hub.enums.ActionType;
import ru.yandex.practicum.collector.model.hub.enums.ConditionOperation;
import ru.yandex.practicum.collector.model.hub.enums.ConditionType;
import ru.yandex.practicum.collector.service.events.HubEventService;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
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

        HubEvent hubEvent;

        ScenarioAddedEventProto s = hubEventProto.getScenarioAdded();
        ScenarioAddedEvent model = new ScenarioAddedEvent();
        model.setName(s.getName());

        Instant timestamp = Instant.ofEpochSecond(
                hubEventProto.getTimestamp().getSeconds(),
                hubEventProto.getTimestamp().getNanos()
        );

        List<ScenarioCondition> mappedConditions = s.getConditionList().stream()
                .map(p -> {
                    ScenarioCondition c = new ScenarioCondition();
                    c.setSensorId(p.getSensorId());
                    c.setType(ConditionType.valueOf(p.getType().name()));
                    c.setOperation(ConditionOperation.valueOf(p.getOperation().name()));
                    if (p.getValueCase() == null || p.getValueCase() == ScenarioConditionProto.ValueCase.VALUE_NOT_SET) {
                        c.setValue(null);
                    } else {
                        switch (p.getValueCase()) {
                            case INT_VALUE -> c.setValue(p.getIntValue());
                            case BOOL_VALUE -> c.setValue(p.getBoolValue() ? 1 : 0);
                        }
                    }
                    return c;
                })
                .collect(Collectors.toList());

        List<DeviceAction> mappedActions = s.getActionList().stream()
                .map(p -> {
                    DeviceAction a = new DeviceAction();
                    a.setSensorId(p.getSensorId());
                    a.setType(ActionType.valueOf(p.getType().name()));
                    a.setValue(p.hasValue() ? p.getValue() : null);
                    return a;
                })
                .collect(Collectors.toList());

        model.setConditions(mappedConditions);
        model.setActions(mappedActions);

        hubEvent = model;

        hubEvent.setHubId(hubEventProto.getHubId());
        hubEvent.setTimestamp(timestamp);
        hubEventService.processEvent(model);
    }
}