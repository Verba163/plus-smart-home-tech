package ru.yandex.practicum.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.feign.decoder.PaymentFeignErrorDecoder;

@Configuration
public class PaymentFeignConfig {
    @Bean
    public ErrorDecoder paymentErrorDecoder() {
        return new PaymentFeignErrorDecoder();
    }
}