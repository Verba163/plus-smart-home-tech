package ru.yandex.practicum.analyzer.service.snapshot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.grpc.GrpcClient;
import ru.yandex.practicum.analyzer.model.Action;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionExecutor {

    private final GrpcClient grpcClient;

    public void executeAction(String sensorId, Action action, String hubId, String scenarioName, Instant timestamp) {
        log.info("Sending scenario action to hubRouter: type={}, value={}, for sensor={}",
                action.getType(), action.getValue(), sensorId);
        grpcClient.sendToHubRouter(sensorId, action, hubId, scenarioName, timestamp);
    }
}
