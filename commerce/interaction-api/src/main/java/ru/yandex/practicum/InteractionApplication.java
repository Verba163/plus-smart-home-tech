package ru.yandex.practicum;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ru.yandex.practicum.feign.clients")
public class InteractionApplication {
    public static void main(String[] args) {
        SpringApplication.run(InteractionApplication.class, args);
    }
}