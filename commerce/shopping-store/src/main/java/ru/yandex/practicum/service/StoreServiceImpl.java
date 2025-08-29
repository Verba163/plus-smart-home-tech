package ru.yandex.practicum.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.StoreRepository;
import ru.yandex.practicum.store.model.SetProductQuantityStateRequest;
import ru.yandex.practicum.store.model.dto.ProductDto;
import ru.yandex.practicum.store.model.enums.ProductCategory;
import ru.yandex.practicum.store.model.enums.ProductState;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<ProductDto> getProductsByCategory(ProductCategory category, Pageable pageable) {
        log.info("Starting to fetch products for category: {}", category);
        log.debug("Using pagination parameters: page number = {}, page size = {}", pageable.getPageNumber(), pageable.getPageSize());
        log.debug("Sorting details: {}", pageable.getSort());

        Page<Product> productPage = storeRepository.findAllByProductCategory(category, pageable);

        log.info("Successfully fetched {} products for category: {}", productPage.getTotalElements(), category);
        log.debug("Product page details: total pages = {}, current page number = {}, page size = {}",
                productPage.getTotalPages(), productPage.getNumber(), productPage.getSize());

        return productPage.map(productMapper::toDto);
    }

    @Transactional
    @Override
    public ProductDto createNewProduct(ProductDto productDto) {
        log.info("Creating new product with name: {}", productDto.getProductName());

        Product product = productMapper.toEntity(productDto);
        product.setProductState(ProductState.ACTIVE);

        Product savedProduct = storeRepository.save(product);
        log.info("Product created with ID: {}", savedProduct.getProductId());

        return productMapper.toDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        UUID productId = productDto.getProductId();
        log.info("Updating product with ID: {}", productId);

        getProductById(productId);

        Product updatedProduct = storeRepository.save(productMapper.toEntity(productDto));
        log.info("Product with ID: {} has been updated", productId);

        return productMapper.toDto(updatedProduct);
    }

    @Transactional
    @Override
    public Boolean deleteProduct(UUID productId) {
        log.info("Deleting (deactivating) product with ID: {}", productId);

        Product product = storeRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Attempted to delete non-existent product with ID: {}", productId);
                    return new NotFoundException("Product not found");
                });

        if (product.getProductState() != ProductState.DEACTIVATE) {
            product.setProductState(ProductState.DEACTIVATE);
            storeRepository.save(product);
            log.info("Product with ID: {} has been deactivated", productId);
            return true;
        } else {
            log.info("Product with ID: {} is already deactivated", productId);
            return false;
        }
    }

    @Transactional
    @Override
    public Boolean updateQuantityState(SetProductQuantityStateRequest request) {
        UUID productId = request.getProductId();
        log.info("Updating quantity state for product ID: {}", productId);

        Product product = getProductById(productId);

        if (!product.getQuantityState().equals(request.getQuantityState())) {
            log.debug("Changing quantity state from {} to {} for product ID: {}",
                    product.getQuantityState(), request.getQuantityState(), productId);
            product.setQuantityState(request.getQuantityState());
            storeRepository.save(product);
            log.info("Quantity state updated for product ID: {}", productId);
            return true;
        } else {
            log.info("No change needed for quantity state of product ID: {}", productId);
            return false;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Product getProductById(UUID productId) {
        log.debug("Fetching product by ID: {}", uuidToString(productId));

        return storeRepository.findById(productId)
                .orElseThrow(() -> {
                    String message = String.format("Fetching product by ID: %s", uuidToString(productId));
                    log.warn(message);
                    return new NotFoundException(message);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDto getStoreProductById(UUID productId) {
        log.debug("Fetching productDto by ID: {}", uuidToString(productId));
        Product product = storeRepository.findById(productId)
                .orElseThrow(() -> {
                    String message = String.format("Fetching product by ID: %s", uuidToString(productId));
                    log.warn(message);
                    return new NotFoundException(message);
                });
        return productMapper.toDto(product);
    }

    private String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : "null";
    }
}