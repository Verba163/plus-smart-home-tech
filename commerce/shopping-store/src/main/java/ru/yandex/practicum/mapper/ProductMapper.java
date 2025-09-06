package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.store.model.dto.ProductDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toEntity(ProductDto dto);
}
