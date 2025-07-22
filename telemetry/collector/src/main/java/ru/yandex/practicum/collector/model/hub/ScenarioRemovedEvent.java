package ru.yandex.practicum.collector.model.hub;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.collector.model.hub.enums.DeviceEventType;


@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioRemovedEvent extends HubEvent {

    @NotNull
    @Size(min = 3)
    String name;

    @Override
    public DeviceEventType getType() {
        return DeviceEventType.SCENARIO_REMOVED;
    }
}