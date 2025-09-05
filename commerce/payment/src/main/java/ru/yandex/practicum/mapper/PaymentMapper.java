package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.model.PaymentModel;
import ru.yandex.practicum.payment.model.dto.PaymentModelDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    PaymentModelDto toDto(PaymentModel paymentModel);
}
