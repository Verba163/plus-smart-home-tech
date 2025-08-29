package ru.yandex.practicum.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.feign.decoder.ShoppingCartFeignErrorDecoder;

@Configuration
public class ShoppingCartFeignConfig {

    @Bean
    public ErrorDecoder shoppingCartErrorDecoder() {
        return new ShoppingCartFeignErrorDecoder();
    }

}