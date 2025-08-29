package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.store.model.SetProductQuantityStateRequest;
import ru.yandex.practicum.store.model.dto.ProductDto;
import ru.yandex.practicum.store.model.enums.ProductCategory;

import java.util.UUID;


public interface StoreService {

    Page<ProductDto> getProductsByCategory(ProductCategory category, Pageable pageable);

    ProductDto createNewProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    Boolean deleteProduct(UUID productId);

    Boolean updateQuantityState(SetProductQuantityStateRequest request);

    Product getProductById(UUID productId);

    ProductDto getStoreProductById(UUID productId);
}
