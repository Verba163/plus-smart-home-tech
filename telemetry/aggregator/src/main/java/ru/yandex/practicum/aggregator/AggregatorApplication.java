package ru.yandex.practicum.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.aggregator.consumer.SensorEventConsumerRunner;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AggregatorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AggregatorApplication.class, args);
        SensorEventConsumerRunner aggregator = context.getBean(SensorEventConsumerRunner.class);
        aggregator.start();
    }
}