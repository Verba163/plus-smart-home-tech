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
            log.info("Failed to extract value for condition type {}", condition.getType());
            return false;
        }

        return switch (condition.getOperation()) {
            case EQUALS -> sensorValue.equals(condition.getValue());
            case GREATER_THAN -> sensorValue > condition.getValue();
            case LOWER_THAN -> sensorValue < condition.getValue();
        };
    }

    private Integer extractSensorValue(ConditionType conditionType, Object indicator) {

        switch (conditionType) {
            case TEMPERATURE:
                if (indicator instanceof ClimateSensorAvro) {
                    return ((ClimateSensorAvro) indicator).getTemperatureC();
                } else if (indicator instanceof TemperatureSensorAvro) {
                    return ((TemperatureSensorAvro) indicator).getTemperatureC();
                } else {
                    return null;
                }
            case HUMIDITY:
                if (indicator instanceof ClimateSensorAvro) {
                    return ((ClimateSensorAvro) indicator).getHumidity();
                } else {
                    return null;
                }
            case CO2LEVEL:
                if (indicator instanceof ClimateSensorAvro) {
                    return ((ClimateSensorAvro) indicator).getCo2Level();
                } else {
                    return null;
                }
            case LUMINOSITY:
                if (indicator instanceof LightSensorAvro) {
                    return ((LightSensorAvro) indicator).getLuminosity();
                } else {
                    return null;
                }
            case MOTION:
                if (indicator instanceof MotionSensorAvro) {
                    return ((MotionSensorAvro) indicator).getMotion() ? 1 : 0;
                } else {
                    return null;
                }
            case SWITCH:
                if (indicator instanceof SwitchSensorAvro) {
                    return ((SwitchSensorAvro) indicator).getState() ? 1 : 0;
                } else {
                    return null;
                }
            default:
                return null;
        }
    }
}
