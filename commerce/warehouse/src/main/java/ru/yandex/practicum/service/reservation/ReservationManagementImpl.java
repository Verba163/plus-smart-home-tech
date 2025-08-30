package ru.yandex.practicum.service.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.cart.model.dto.ShoppingCartDto;
import ru.yandex.practicum.exception.NoProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.model.Reservation;
import ru.yandex.practicum.model.Warehouse;
import ru.yandex.practicum.repository.ReservationRepository;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.warehouse.model.dto.BookedProductsDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationManagementImpl implements ReservationManagement {

    private final WarehouseRepository itemRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public void cancelReservation(UUID shoppingCartId) {
        log.info("Cancelling reservation for shopping cart {}", shoppingCartId);

        List<Reservation> reservations = reservationRepository.findByShoppingCartId(shoppingCartId);

        Map<UUID, Warehouse> products = itemRepository.findAllById(
                reservations.stream().map(Reservation::getProductId).toList()
        ).stream().collect(Collectors.toMap(Warehouse::getProductId, Function.identity()));

        for (Reservation reservation : reservations) {
            Warehouse item = products.get(reservation.getProductId());
            if (item == null) {
                String errorMsg = String.format("Product with ID %s not found during reservation cancellation", reservation.getProductId());
                log.error(errorMsg);
                throw new NoProductInWarehouseException(errorMsg, errorMsg);
            }

            item.setQuantity(item.getQuantity() + reservation.getReservedQuantity());
            item.setReservedQuantity(item.getReservedQuantity() - reservation.getReservedQuantity());
            itemRepository.save(item);

            log.info("Reservation of {} units for product {} in shopping cart {} has been canceled",
                    reservation.getReservedQuantity(), reservation.getProductId(), shoppingCartId);

            reservationRepository.delete(reservation);
        }

        log.info("All reservations for shopping cart {} have been successfully canceled", shoppingCartId);
    }

    @Override
    @Transactional
    public BookedProductsDto checkProductQuantity(ShoppingCartDto cart) {
        log.info("Checking product availability for shopping cart: {}", cart.getId());

        Map<UUID, Warehouse> products = itemRepository.findAllById(cart.getProducts().keySet())
                .stream()
                .collect(Collectors.toMap(Warehouse::getProductId, Function.identity()));

        double totalWeight = 0;
        double totalVolume = 0;
        boolean hasFragileItems = false;
        List<UUID> missingProducts = new ArrayList<>();

        for (Map.Entry<UUID, Integer> entry : cart.getProducts().entrySet()) {
            UUID productId = entry.getKey();
            long requestedQuantity = entry.getValue();

            Warehouse product = products.get(productId);
            if (product == null) {
                String message = String.format("Product with ID %s not found in warehouse", productId);
                log.info(message);
                throw new NoProductInWarehouseException(message, message);
            }

            if (product.getQuantity() < requestedQuantity) {
                log.info("Insufficient stock for product {}: available {}, requested {}",
                        productId, product.getQuantity(), requestedQuantity);
                missingProducts.add(productId);
            } else {
                totalWeight += product.getWeight() * requestedQuantity;
                totalVolume += (product.getWidth() * product.getHeight() * product.getDepth()) * requestedQuantity;
                hasFragileItems = hasFragileItems || product.getFragile();
            }
        }

        if (!missingProducts.isEmpty()) {
            log.info("Insufficient stock for shopping cart {}: {}", cart.getId(), missingProducts);
            throw new ProductInShoppingCartLowQuantityInWarehouse(
                    "Insufficient products in warehouse",
                    missingProducts
            );
        }

        for (Map.Entry<UUID, Integer> entry : cart.getProducts().entrySet()) {
            UUID productId = entry.getKey();
            long requestedQuantity = entry.getValue();

            Warehouse product = products.get(productId);
            product.setQuantity(product.getQuantity() - requestedQuantity);
            product.setReservedQuantity(product.getReservedQuantity() + requestedQuantity);
            itemRepository.save(product);

            Reservation reservation = new Reservation();
            reservation.setShoppingCartId(cart.getId());
            reservation.setProductId(productId);
            reservation.setReservedQuantity(requestedQuantity);
            reservationRepository.save(reservation);

            log.info("Reserved {} units of product {} for shopping cart {}", requestedQuantity, productId,
                    cart.getId());
        }

        BookedProductsDto result = new BookedProductsDto();
        result.setDeliveryWeight(totalWeight);
        result.setDeliveryVolume(totalVolume);
        result.setFragile(hasFragileItems);

        log.info("Reservation completed successfully for shopping cart {}. Total weight: {}, volume: {}, fragile: {}",
                cart.getId(), totalWeight, totalVolume, hasFragileItems);

        return result;
    }
}
