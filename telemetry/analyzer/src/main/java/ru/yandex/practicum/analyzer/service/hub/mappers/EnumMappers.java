package ru.yandex.practicum.analyzer.service.hub.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.model.enums.ActionType;
import ru.yandex.practicum.analyzer.model.enums.ConditionOperation;
import ru.yandex.practicum.analyzer.model.enums.ConditionType;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;

@Component
public class EnumMappers {
    public ConditionType ConditionTypeMapper(ConditionTypeAvro avroType) {
        return switch (avroType) {
            case MOTION -> ConditionType.MOTION;
            case LUMINOSITY -> ConditionType.LUMINOSITY;
            case SWITCH -> ConditionType.SWITCH;
            case TEMPERATURE -> ConditionType.TEMPERATURE;
            case CO2LEVEL -> ConditionType.CO2LEVEL;
            case HUMIDITY -> ConditionType.HUMIDITY;
        };
    }

    public ConditionOperation ConditionOperationMapper(ConditionOperationAvro avroOperation) {
        return switch (avroOperation) {
            case EQUALS -> ConditionOperation.EQUALS;
            case GREATER_THAN -> ConditionOperation.BIGGER_THAN;
            case LOWER_THAN -> ConditionOperation.LOWER_THAN;
        };
    }

    public ActionType ActionTypeMapper(ActionTypeAvro avroType) {
        return switch (avroType) {
            case ACTIVATE -> ActionType.ACTIVATE;
            case DEACTIVATE -> ActionType.DEACTIVATE;
            case INVERSE -> ActionType.INVERSE;
            case SET_VALUE -> ActionType.SET_VALUE;
        };
    }
}
