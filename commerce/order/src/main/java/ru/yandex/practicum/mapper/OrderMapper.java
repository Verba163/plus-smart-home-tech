package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.model.OrderModel;
import ru.yandex.practicum.order.model.dto.OrderModelDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    OrderModelDto toDto(OrderModel orderModel);
}