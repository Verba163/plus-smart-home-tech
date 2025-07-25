package ru.yandex.practicum.collector.service.events.mappers;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.model.hub.HubEvent;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HubEventMapperFactory {

    private final List<HubEventMapper> mappers;

    public SpecificRecordBase mapToAvro(HubEvent event) {
        return mappers.stream()
                .filter(m -> m.supports(event.getType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Unknown sensor type: %s", event.getType())
                ))
                .mapToAvro(event);
    }
}
