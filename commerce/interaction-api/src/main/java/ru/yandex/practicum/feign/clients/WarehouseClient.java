package ru.yandex.practicum.feign.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.model.dto.ShoppingCartDto;
import ru.yandex.practicum.delivery.model.ShippedToDeliveryRequest;
import ru.yandex.practicum.feign.config.WarehouseFeignConfig;
import ru.yandex.practicum.order.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.warehouse.model.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.model.dto.AddressDto;
import ru.yandex.practicum.warehouse.model.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.model.dto.NewProductInWarehouseRequest;

import java.util.Map;
import java.util.UUID;

import static ru.yandex.practicum.constants.clients.FeignClientsConstants.*;

@FeignClient(name = "warehouse", configuration = WarehouseFeignConfig.class)
public interface WarehouseClient {

    @PutMapping(BASE_PATH_WAREHOUSE)
    void addNewProduct(@RequestBody NewProductInWarehouseRequest request);

    @PostMapping(CHECK_PRODUCT_QUANTITY)
    BookedProductsDto checkProductQuantity(@RequestBody ShoppingCartDto cart);

    @PostMapping(ADD_PRODUCT_TO_WAREHOUSE)
    void addProductToWarehouse(@RequestBody AddProductToWarehouseRequest request);

    @GetMapping(ADDRESS)
    AddressDto getWarehouseAddress();

    @DeleteMapping(RESERVATION)
    void cancelReservation(@RequestParam UUID shoppingCartId);

    @PostMapping(SHIPPED_PATH)
    void processShipmentToDelivery(@RequestBody ShippedToDeliveryRequest request);

    @PostMapping(ASSEMBLY_PATH_WAREHOUSE)
    BookedProductsDto assembleProductsForDelivery(@RequestBody AssemblyProductsForOrderRequest orderRequest);

    @PostMapping(RETURN_PATH_WAREHOUSE)
    void acceptReturn(@RequestBody Map<UUID, Long> returnedProducts);
}

