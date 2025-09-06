package ru.yandex.practicum.service.products;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.NoProductInWarehouseException;
import ru.yandex.practicum.exception.ProductAlreadyExistsInWarehouseException;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.model.Warehouse;
import ru.yandex.practicum.repository.ReservationRepository;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.warehouse.model.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.model.dto.NewProductInWarehouseRequest;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductManagementImpl implements ProductManagement {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Transactional
    @Override
    public void addNewProduct(NewProductInWarehouseRequest request) {
        log.info("Attempting to add a new product to the warehouse: {}", request);

        if (warehouseRepository.existsById(request.getProductId())) {
            String message = String.format("Product with ID %s already exists in the warehouse.", request.getProductId());
            log.info(message);
            throw new ProductAlreadyExistsInWarehouseException(message, message);
        }

        warehouseRepository.save(warehouseMapper.toWarehouse(request));
        String successMessage = String.format("Product with ID %s has been successfully added to the warehouse.", request.getProductId());
        log.info(successMessage);
    }


    @Override
    @Transactional
    public void addProductQuantity(AddProductToWarehouseRequest request) {
        Warehouse product = findProductById(request.getProductId());
        product.setQuantity(product.getQuantity() + request.getQuantity());
        warehouseRepository.save(product);
    }

    private Warehouse findProductById(UUID productId) {
        return warehouseRepository.findById(productId)
                .orElseThrow(() -> new NoProductInWarehouseException(
                        String.format("Product with ID %s not found in warehouse", productId),
                        String.format("Product with ID %s not found in warehouse", productId)));
    }

}
