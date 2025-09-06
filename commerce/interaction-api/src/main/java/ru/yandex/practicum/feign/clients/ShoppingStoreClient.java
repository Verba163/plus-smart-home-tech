package ru.yandex.practicum.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.feign.config.ShoppingStoreFeignConfig;
import ru.yandex.practicum.store.model.dto.PageableDto;
import ru.yandex.practicum.store.model.dto.ProductDto;
import ru.yandex.practicum.store.model.enums.ProductCategory;
import ru.yandex.practicum.store.model.enums.QuantityState;

import java.util.UUID;

import static ru.yandex.practicum.constants.clients.FeignClientsConstants.*;

@FeignClient(name = "shopping-store", configuration = ShoppingStoreFeignConfig.class)
public interface ShoppingStoreClient {

    @GetMapping(BASE_PATH_CART)
    PageableDto<ProductDto> getProducts(
            @RequestParam("category") ProductCategory category,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "sort", required = false) String sort);

    @PutMapping(BASE_PATH)
    ProductDto createNewProduct(@RequestBody ProductDto productDto);

    @PostMapping(BASE_PATH)
    ProductDto updateProduct(@RequestBody ProductDto productDto);

    @PostMapping(REMOVE_PRODUCT)
    Boolean deleteProduct(@RequestBody UUID productId);

    @PostMapping(QUANTITY_STATE)
    Boolean updateQuantityState(
            @RequestParam("product-id") UUID productId,
            @RequestParam("quantityState") QuantityState quantityState);

    @GetMapping(BASE_PATH + "/{product-id}")
    ProductDto getProduct(@PathVariable("product-id") UUID productId);
}