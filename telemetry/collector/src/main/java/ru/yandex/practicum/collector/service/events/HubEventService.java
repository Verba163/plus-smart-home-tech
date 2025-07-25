package ru.yandex.practicum.collector.service.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.collector.model.hub.HubEvent;
import ru.yandex.practicum.collector.model.hub.*;
import ru.yandex.practicum.collector.model.hub.enums.ActionType;
import ru.yandex.practicum.collector.model.hub.enums.ConditionOperation;
import ru.yandex.practicum.collector.model.hub.enums.ConditionType;
import ru.yandex.practicum.collector.model.hub.enums.DeviceType;
import ru.yandex.practicum.collector.service.events.mappers.HubEventMapperFactory;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class HubEventService {

    private final Producer<String, SpecificRecordBase> kafkaProducer;
    private final HubEventMapperFactory mapperFactory;

    @Value("${topic.hub-events}")
    private String hubEventsTopic;

    public void processEvent(HubEvent hubEvent) {
        try {
            SpecificRecordBase payload = mapperFactory.mapToAvro(hubEvent);

            HubEventAvro avro = HubEventAvro.newBuilder()
                    .setHubId(hubEvent.getHubId())
                    .setTimestamp(hubEvent.getTimestamp() != null ? hubEvent.getTimestamp() : Instant.now())
                    .setPayload(payload)
                    .build();

            log.info("Sending HubEvent to Kafka with payload: {}", payload.getClass().getSimpleName());
            kafkaProducer.send(new ProducerRecord<>(hubEventsTopic, avro.getHubId(), avro));
        } catch (Exception e) {
            log.error("Error processing hub event: {}", hubEvent.getHubId(), e);
        }
    }

    public void handleHubEvent(HubEventProto proto) {
        HubEvent event;
        Instant ts = Instant.ofEpochSecond(proto.getTimestamp().getSeconds(), proto.getTimestamp().getNanos());

        switch (proto.getPayloadCase()) {
            case DEVICE_ADDED -> {
                DeviceAddedEventProto d = proto.getDeviceAdded();
                DeviceAddedEvent model = new DeviceAddedEvent();
                model.setId(d.getId());
                model.setDeviceType(DeviceType.valueOf(d.getType().name()));
                event = model;
            }
            case DEVICE_REMOVED -> {
                DeviceRemovedEventProto d = proto.getDeviceRemoved();
                DeviceRemovedEvent model = new DeviceRemovedEvent();
                model.setId(d.getId());
                event = model;
            }
            case SCENARIO_ADDED -> {
                ScenarioAddedEventProto s = proto.getScenarioAdded();
                ScenarioAddedEvent model = new ScenarioAddedEvent();
                model.setName(s.getName());

                List<ScenarioCondition> mappedConditions = s.getConditionList().stream()
                        .map(p -> {
                            ScenarioCondition c = new ScenarioCondition();
                            c.setSensorId(p.getSensorId());
                            c.setType(ConditionType.valueOf(p.getType().name()));
                            c.setOperation(ConditionOperation.valueOf(p.getOperation().name()));
                            if (p.getValueCase() == null || p.getValueCase() == ScenarioConditionProto.ValueCase.VALUE_NOT_SET) {
                                c.setValue(0);
                            } else {
                                switch (p.getValueCase()) {
                                    case INT_VALUE -> c.setValue(p.getIntValue());
                                    case BOOL_VALUE -> c.setValue(p.getBoolValue() ? 1 : 0);
                                }
                            }
                            return c;
                        })
                        .collect(Collectors.toList());

                List<DeviceAction> mappedActions = s.getActionList().stream()
                        .map(p -> {
                            DeviceAction a = new DeviceAction();
                            a.setSensorId(p.getSensorId());
                            a.setType(ActionType.valueOf(p.getType().name()));
                            a.setValue(p.hasValue() ? p.getValue() : null);
                            return a;
                        })
                        .collect(Collectors.toList());

                model.setConditions(mappedConditions);
                model.setActions(mappedActions);

                event = model;
            }

            case SCENARIO_REMOVED -> {
                ScenarioRemovedEventProto s = proto.getScenarioRemoved();
                ScenarioRemovedEvent model = new ScenarioRemovedEvent();
                model.setName(s.getName());
                event = model;
            }
            default -> {
                log.warn("Неизвестный тип HubEvent: {}", proto.getPayloadCase());
                return;
            }
        }

        event.setHubId(proto.getHubId());
        event.setTimestamp(ts);
        processEvent(event);
    }

}