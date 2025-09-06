package ru.yandex.practicum.service.products;

import ru.yandex.practicum.warehouse.model.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.model.dto.NewProductInWarehouseRequest;

public interface ProductManagement {
    void addNewProduct(NewProductInWarehouseRequest request);

    void addProductQuantity(AddProductToWarehouseRequest request);

}
