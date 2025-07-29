package ru.yandex.practicum.analyzer.service.snapshot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.model.Condition;
import ru.yandex.practicum.analyzer.model.enums.ConditionType;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Slf4j
@Component
public class ConditionChecker {

    public boolean checkCondition(Condition condition, Object indicator) {
        Integer sensorValue = extractSensorValue(condition.getType(), indicator);
        if (sensorValue == null) {
            log.info("Failed to extract value for condition type {} with indicator {}", condition.getType(), indicator);
            return false;
        }

        Integer conditionValue = condition.getValue();
        if (conditionValue == null) {
            log.warn("Condition value is null for condition {}", condition);
            return false;
        }

        return switch (condition.getOperation()) {
            case EQUALS -> sensorValue.equals(conditionValue);
            case GREATER_THAN -> sensorValue > conditionValue;
            case LOWER_THAN -> sensorValue < conditionValue;
            default -> {
                log.warn("Unknown operation {} for condition {}", condition.getOperation(), condition);
                yield false;
            }
        };
    }

    private Integer extractSensorValue(ConditionType conditionType, Object indicator) {
        if (indicator == null) {
            log.warn("Indicator is null for type {}", conditionType);
            return null;
        }

        switch (conditionType) {
            case TEMPERATURE:
                if (indicator instanceof ClimateSensorAvro) {
                    return ((ClimateSensorAvro) indicator).getTemperatureC();
                } else if (indicator instanceof TemperatureSensorAvro) {
                    return ((TemperatureSensorAvro) indicator).getTemperatureC();
                } else {
                    log.debug("Indicator of type {} is not a temperature sensor: {}", indicator.getClass().getSimpleName(), indicator);
                    return null;
                }
            case HUMIDITY:
                if (indicator instanceof ClimateSensorAvro) {
                    return ((ClimateSensorAvro) indicator).getHumidity();
                } else {
                    log.debug("Indicator of type {} is not a climate sensor: {}", indicator.getClass().getSimpleName(), indicator);
                    return null;
                }
            case CO2LEVEL:
                if (indicator instanceof ClimateSensorAvro) {
                    return ((ClimateSensorAvro) indicator).getCo2Level();
                } else {
                    log.debug("Indicator of type {} is not a climate sensor: {}", indicator.getClass().getSimpleName(), indicator);
                    return null;
                }
            case LUMINOSITY:
                if (indicator instanceof LightSensorAvro) {
                    return ((LightSensorAvro) indicator).getLuminosity();
                } else {
                    log.debug("Indicator of type {} is not a light sensor: {}", indicator.getClass().getSimpleName(), indicator);
                    return null;
                }
            case MOTION:
                if (indicator instanceof MotionSensorAvro) {
                    return ((MotionSensorAvro) indicator).getMotion() ? 1 : 0;
                } else {
                    log.debug("Indicator of type {} is not a motion sensor: {}", indicator.getClass().getSimpleName(), indicator);
                    return null;
                }
            case SWITCH:
                if (indicator instanceof SwitchSensorAvro) {
                    return ((SwitchSensorAvro) indicator).getState() ? 1 : 0;
                } else {
                    log.debug("Indicator of type {} is not a switch sensor: {}", indicator.getClass().getSimpleName(), indicator);
                    return null;
                }
            default:
                log.warn("Unknown ConditionType: {}", conditionType);
                return null;
        }
    }
}