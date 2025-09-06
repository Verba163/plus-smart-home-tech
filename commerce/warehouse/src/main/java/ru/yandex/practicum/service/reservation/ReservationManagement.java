package ru.yandex.practicum.service.reservation;

import ru.yandex.practicum.cart.model.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.model.dto.BookedProductsDto;

import java.util.UUID;

public interface ReservationManagement {
    BookedProductsDto checkProductQuantity(ShoppingCartDto cart);

    void cancelReservation(UUID shoppingCartId);
}
