package ru.yandex.practicum.collector.service.events.mappers;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.hub.enums.DeviceEventType;

public interface HubEventMapper {
    boolean supports(DeviceEventType type);

    SpecificRecordBase mapToAvro(HubEvent event);
}