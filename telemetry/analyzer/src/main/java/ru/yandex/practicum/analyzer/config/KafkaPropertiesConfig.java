package ru.yandex.practicum.analyzer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "analyzer.kafka")
public class KafkaPropertiesConfig {
    private Map<String, String> consumer;
    private Map<String, String> producer;
}