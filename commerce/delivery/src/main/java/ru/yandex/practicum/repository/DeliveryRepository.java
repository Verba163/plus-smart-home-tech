package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.DeliveryModel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryModel, UUID> {

    Optional<DeliveryModel> findByOrderId(UUID orderId);

    Optional<DeliveryModel> findByDeliveryId(UUID deliveryModelId);
}