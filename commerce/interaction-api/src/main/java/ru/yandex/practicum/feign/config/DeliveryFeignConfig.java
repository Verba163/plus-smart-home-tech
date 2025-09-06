package ru.yandex.practicum.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.feign.decoder.DeliveryFeignErrorDecoder;

@Configuration
public class DeliveryFeignConfig {
    @Bean
    public ErrorDecoder deliveryErrorDecoder() {
        return new DeliveryFeignErrorDecoder();
    }
}