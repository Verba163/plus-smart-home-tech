package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.model.dto.ShoppingCartDto;
import ru.yandex.practicum.service.address.AddressProvider;
import ru.yandex.practicum.service.products.ProductManagement;
import ru.yandex.practicum.service.reservation.ReservationManagement;
import ru.yandex.practicum.warehouse.model.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.model.dto.AddressDto;
import ru.yandex.practicum.warehouse.model.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.model.dto.NewProductInWarehouseRequest;

import java.util.UUID;

import static ru.yandex.practicum.constants.WarehouseApiPaths.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class WarehouseController {

    private final AddressProvider addressProvider;
    private final ProductManagement productManagement;
    private final ReservationManagement reservationManagement;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewProduct(@RequestBody @Valid NewProductInWarehouseRequest request) {
        log.info("Received request to add a new product to the warehouse: {}", request);
        productManagement.addNewProduct(request);
    }

    @PostMapping(CHECK)
    @ResponseStatus(HttpStatus.OK)
    public BookedProductsDto checkProductAvailability(@RequestBody @Valid ShoppingCartDto cart) {
        log.info("Checking product availability for shopping cart: {}", cart);
        return reservationManagement.checkProductQuantity(cart);
    }

    @PostMapping(ADD)
    @ResponseStatus(HttpStatus.OK)
    public void addProductQuantity(@RequestBody @Valid AddProductToWarehouseRequest request) {
        log.info("Received request to add product quantity: {}", request);
        productManagement.addProductQuantity(request);
    }

    @GetMapping(ADDRESS)
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getWarehouseAddress() {
        log.info("Received request to get warehouse address");
        return addressProvider.getWarehouseAddress();
    }

    @DeleteMapping(RESERVATION)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@RequestParam UUID shoppingCartId) {
        log.info("Received request to cancel reservation for cart: {}", shoppingCartId);
        reservationManagement.cancelReservation(shoppingCartId);
    }
}