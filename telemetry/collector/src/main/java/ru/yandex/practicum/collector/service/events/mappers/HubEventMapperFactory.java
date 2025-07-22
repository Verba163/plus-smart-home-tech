package ru.yandex.practicum.collector.service.events.mappers;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.collector.model.hub.HubEvent;

import java.util.List;

@Service
public class HubEventMapperFactory {

    private final List<HubEventMapper> mappers;

    public HubEventMapperFactory(List<HubEventMapper> mappers) {
        this.mappers = mappers;
    }

    public SpecificRecordBase map(HubEvent event) {
        return mappers.stream()
                .filter(m -> m.supports(event.getType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Unknown sensor type: %s", event.getType())
                ))
                .mapToAvro(event);
    }
}
