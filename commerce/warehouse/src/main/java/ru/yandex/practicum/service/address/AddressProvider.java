package ru.yandex.practicum.service.address;

import ru.yandex.practicum.warehouse.model.dto.AddressDto;

public interface AddressProvider {
    AddressDto getWarehouseAddress();
}
