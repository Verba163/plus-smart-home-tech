package ru.yandex.practicum.service.products;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.NoProductInWarehouseException;
import ru.yandex.practicum.exception.ProductAlreadyExistsInWarehouseException;
import ru.yandex.practicum.model.Warehouse;
import ru.yandex.practicum.repository.ReservationRepository;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.warehouse.model.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.model.dto.NewProductInWarehouseRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductManagementImpl implements ProductManagement {

    private final WarehouseRepository itemRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void addNewProduct(NewProductInWarehouseRequest request) {
        log.info("Attempting to add a new product to the warehouse: {}", request);

        if (itemRepository.existsById(request.getProductId())) {
            String message = String.format("Product with ID %s already exists in the warehouse.", request.getProductId());
            log.info(message);
            throw new ProductAlreadyExistsInWarehouseException(message, message);
        }

        Warehouse newItem = Warehouse.builder()
                .id(request.getProductId())
                .fragile(request.getFragile())
                .width(request.getDimension().getWidth())
                .height(request.getDimension().getHeight())
                .depth(request.getDimension().getDepth())
                .weight(request.getWeight())
                .quantity(null)
                .build();

        itemRepository.save(newItem);
        String successMessage = String.format("Product with ID %s has been successfully added to the warehouse.", request.getProductId());
        log.info(successMessage);
    }


    @Override
    @Transactional
    public void addProductQuantity(AddProductToWarehouseRequest request) {
        log.info("Attempting to add {} units to product {} in warehouse", request.getQuantity(), request.getProductId());

        Warehouse item = itemRepository.findById(request.getProductId())
                .orElseThrow(() -> {
                    String message = String.format("Product with ID %s not found in warehouse", request.getProductId());
                    log.error(message);
                    return new NoProductInWarehouseException(message, message);
                });

        item.setQuantity(item.getQuantity() + request.getQuantity());
        itemRepository.save(item);

        String successMessage = String.format("Successfully added %d units to product %s. New quantity: %d",
                request.getQuantity(), request.getProductId(), item.getQuantity());
        log.info(successMessage);
    }

}
