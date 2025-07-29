package ru.yandex.practicum.analyzer.service.hub.config;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

@Component
public class ConditionValidationService {

    public Integer getConditionValue(ScenarioConditionAvro conditionAvro) {
        Object value = conditionAvro.getValue();
        return switch (value) {
            case null -> null;
            case Integer intValue -> intValue;
            case Boolean boolValue -> boolValue ? 1 : 0;
            default -> throw new IllegalArgumentException(
                    String.format("Unsupported condition value type: %s, expected Integer or Boolean",
                            value.getClass().getName())
            );
        };
    }
}
