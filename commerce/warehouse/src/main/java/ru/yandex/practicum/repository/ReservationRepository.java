package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Reservation;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByShoppingCartId(UUID shoppingCartId);
}