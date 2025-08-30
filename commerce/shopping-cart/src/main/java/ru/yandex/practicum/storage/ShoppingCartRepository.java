package ru.yandex.practicum.storage;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import ru.yandex.practicum.model.ShoppingCart;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {

    Optional<ShoppingCart> findCartByUserName(String userName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ShoppingCart> findWithLockingByUserName(String userName);
}
