package ru.yandex.practicum.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.feign.decoder.ShoppingStoreFeignErrorDecoder;

@Configuration
public class ShoppingStoreFeignConfig {

    @Bean
    public ErrorDecoder shoppingStoreErrorDecoder() {
        return new ShoppingStoreFeignErrorDecoder();
    }
}
