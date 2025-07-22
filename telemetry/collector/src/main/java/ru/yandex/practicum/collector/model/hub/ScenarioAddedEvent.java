package ru.yandex.practicum.collector.model.hub;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.collector.model.hub.enums.DeviceEventType;

import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioAddedEvent extends HubEvent {

    @NotNull
    @Size(min = 3)
    String name;

    @NotNull
    @Size(min = 1)
    List<ScenarioCondition> conditions;

    @NotNull
    @Size(min = 1)
    List<DeviceAction> actions;

    @Override
    public DeviceEventType getType() {
        return DeviceEventType.SCENARIO_ADDED;
    }
}