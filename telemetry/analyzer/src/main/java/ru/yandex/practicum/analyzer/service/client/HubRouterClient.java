package ru.yandex.practicum.analyzer.service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;

@Slf4j
@Service
public class HubRouterClient {

    private final HubRouterControllerGrpc.HubRouterControllerBlockingStub stub;

    public HubRouterClient(HubRouterControllerGrpc.HubRouterControllerBlockingStub stub) {
        this.stub = stub;
    }

    public void sendAction(DeviceActionRequest request) {
        try {
            var response = stub.handleDeviceAction(request);
            log.info("DeviceActionResponse received: {}", response);
        } catch (Exception e) {
            log.error("Failed to send DeviceActionRequest: {}", request, e);
            throw e;
        }
    }
}

