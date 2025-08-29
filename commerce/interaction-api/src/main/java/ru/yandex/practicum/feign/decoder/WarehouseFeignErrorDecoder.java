package ru.yandex.practicum.feign.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import ru.yandex.practicum.exception.*;

import java.io.IOException;

public class WarehouseFeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            ErrorResponse errorResponse = objectMapper.readValue(
                    response.body().asInputStream(), ErrorResponse.class);
            String httpStatus = errorResponse.getHttpStatus();
            String message = errorResponse.getMessage();
            String userMessage = errorResponse.getUserMessage();
            String ex = errorResponse.getEx();

            if (response.status() == 400 && "400 BAD_REQUEST".equals(httpStatus)) {
                switch (ex) {
                    case "NoProductInWarehouseException" -> {
                        return new ProductAlreadyExistsInWarehouseException(message, userMessage);
                    }
                    case "NoSpecifiedProductInWarehouseException" -> {
                        return new NoProductInWarehouseException(message, userMessage);
                    }
                    case "ProductInShoppingCartLowQuantityInWarehouse" -> {
                        ErrorResponseForWarehouseCheck errorResponseCheck = (ErrorResponseForWarehouseCheck) errorResponse;
                        return new ProductInShoppingCartLowQuantityInWarehouse(message,
                                errorResponseCheck.getMissingProducts());
                    }
                }
                return new IllegalArgumentException(message);
            }
        } catch (IOException e) {
            return new FeignException.InternalServerError(
                    "Failed to parse error response: " + e.getMessage(),
                    response.request(), null, null);
        }

        return defaultDecoder.decode(methodKey, response);
    }
}