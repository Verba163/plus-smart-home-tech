package ru.yandex.practicum.analyzer.service.hub.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventHandler implements HubEventHandler<ScenarioRemovedEventAvro> {

    private final ScenarioRepository scenarioRepository;

    @Override
    public void handle(String hubId, ScenarioRemovedEventAvro event) {

        String name = event.getName();
        if (!scenarioRepository.existsByName(name)) {
            log.info("Scenario with name '{}' not found", name);
            return;
        }

        scenarioRepository.deleteByName(name);
        log.info("Scenario removed: {}", name);
    }
}

