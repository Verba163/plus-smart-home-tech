package ru.yandex.practicum.feign.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import ru.yandex.practicum.exception.*;

import java.io.IOException;

public class ShoppingCartFeignErrorDecoder implements ErrorDecoder {
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

            switch (response.status()) {
                case 401:
                    if ("401 UNAUTHORIZED".equals(httpStatus)) {
                        return new NotAuthorizedUserException(message, userMessage);
                    }
                    break;
                case 400:
                    if ("400 BAD_REQUEST".equals(httpStatus)) {
                        if (ex.equals("NoProductsInShoppingCartException")) {
                            return new NoProductsInShoppingCartException(message, userMessage);
                        } else if (ex.equals("ProductInShoppingCartLowQuantityInWarehouse")) {
                            ErrorResponseForWarehouseCheck errorResponseCheck = (ErrorResponseForWarehouseCheck) errorResponse;
                            return new ProductInShoppingCartLowQuantityInWarehouse(message,
                                    errorResponseCheck.getMissingProducts());
                        }
                        return new IllegalArgumentException(message);
                    }
                    break;
            }
        } catch (IOException e) {
            return new FeignException.InternalServerError(
                    "Failed to parse error response: " + e.getMessage(),
                    response.request(), null, null);
        }

        return defaultDecoder.decode(methodKey, response);
    }
}