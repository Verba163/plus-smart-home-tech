package ru.yandex.practicum.aggregator.consumer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SensorEventConsumerRunner {

    private final SensorEventHandler eventHandler;
    private final KafkaConsumer<String, SensorEventAvro> consumer;
    private static final String TOPIC = "telemetry.sensors.v1";
    private static final Duration POLL_TIMEOUT = Duration.ofMillis(1000);

    private Thread consumerThread;

    @PostConstruct
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));

        consumerThread = new Thread(() -> {
            try {
                consumer.subscribe(List.of(TOPIC));
                while (true) {
                    ConsumerRecords<String, SensorEventAvro> records = consumer.poll(POLL_TIMEOUT);
                    eventHandler.handle(records);
                }
            } catch (WakeupException ignored) {

            } finally {
                eventHandler.shutdown();
            }
        }, "sensor-event-consumer-thread");

        consumerThread.start();
    }

    @PreDestroy
    public void stop() {
        consumer.wakeup();
        try {
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
