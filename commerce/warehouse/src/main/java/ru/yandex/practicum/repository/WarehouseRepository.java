package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Warehouse;

import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
}