package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.constants.ShoppingStoreApiPaths;
import ru.yandex.practicum.service.StoreService;
import ru.yandex.practicum.store.model.SetProductQuantityStateRequest;
import ru.yandex.practicum.store.model.dto.PageableDto;
import ru.yandex.practicum.store.model.dto.ProductDto;
import ru.yandex.practicum.store.model.enums.ProductCategory;

import java.util.UUID;

import static ru.yandex.practicum.constants.ShoppingStoreApiPaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class ShoppingStoreController {

    private final StoreService storeService;

    @GetMapping
    public PageableDto<ProductDto> getProductByCategory(@RequestParam ProductCategory category, @Valid Pageable pageable) {
        Page<ProductDto> resultPage = storeService.getProductsByCategory(category, pageable);
        return new PageableDto<>(resultPage);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        return storeService.createNewProduct(productDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return storeService.updateProduct(productDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(REMOVE_PRODUCT_FROM_STORE)
    public boolean removeProductFromStore(@RequestBody UUID productId) {
        return storeService.deleteProduct(productId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(QUANTITY_STATE)
    public boolean updateQuantityState(SetProductQuantityStateRequest request) {
        return storeService.updateQuantityState(request);
    }

    @GetMapping("/{product-id}")
    public ProductDto getStoreProductById(@PathVariable("product-id") UUID productId) {
        return storeService.getStoreProductById(productId);
    }
}