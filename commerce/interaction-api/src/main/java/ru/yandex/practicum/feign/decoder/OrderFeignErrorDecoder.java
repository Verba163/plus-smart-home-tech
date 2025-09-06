package ru.yandex.practicum.feign.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.ErrorResponse;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.ProductNotFoundException;

import java.io.IOException;

@Slf4j
@Component
public class OrderFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.body() == null) {
                return new FeignException.InternalServerError("Empty response body from Feign",
                        response.request(), null, null);
            }

            ErrorResponse errorResponse = objectMapper.readValue(
                    response.body().asInputStream(), ErrorResponse.class);

            String httpStatus = errorResponse.getHttpStatus();
            String message = errorResponse.getMessage();
            String userMessage = errorResponse.getUserMessage();
            String ex = errorResponse.getEx();

            if (response.status() == 404) {
                if ("404 NOT_FOUND".equals(httpStatus)) {
                    switch (ex) {
                        case "NoOrderFoundException":
                            return new NoOrderFoundException(message, userMessage);
                        case "ProductNotFoundException":
                            return new ProductNotFoundException(message, userMessage);
                        case "NoDeliveryFoundException":
                            return new NoDeliveryFoundException(message, userMessage);
                    }
                }
            } else {
                log.error("Unhandled Feign error: status={}, ex={}, message={}", httpStatus, ex, message);
                return new IllegalStateException("Unhandled Feign error: " + message);
            }

        } catch (IOException e) {
            log.error("Error parsing Feign response body", e);
            return new FeignException.InternalServerError(
                    "Failed to parse error response: " + e.getMessage(),
                    response.request(), null, null);
        }

        return defaultDecoder.decode(methodKey, response);
    }
}