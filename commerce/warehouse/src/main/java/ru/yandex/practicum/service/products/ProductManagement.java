package ru.yandex.practicum.service.products;

import ru.yandex.practicum.warehouse.model.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.model.dto.NewProductInWarehouseRequest;

import java.util.Map;
import java.util.UUID;

public interface ProductManagement {
    void addNewProduct(NewProductInWarehouseRequest request);

    void addProductQuantity(AddProductToWarehouseRequest request);

    void acceptReturn(Map<UUID, Long> returnedProducts);

}
