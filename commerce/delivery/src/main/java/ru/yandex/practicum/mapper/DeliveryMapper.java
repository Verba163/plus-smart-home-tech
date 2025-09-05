package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.delivery.model.dto.DeliveryModelDto;
import ru.yandex.practicum.model.DeliveryModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryMapper {
    DeliveryModelDto toDto(DeliveryModel deliveryModel);

    DeliveryModel toEntity(DeliveryModelDto deliveryModelDto);
}
