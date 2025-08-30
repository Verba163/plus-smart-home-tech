package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.model.Warehouse;
import ru.yandex.practicum.warehouse.model.dto.NewProductInWarehouseRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseMapper {

    @Mapping(target = "width", source = "dimension.width")
    @Mapping(target = "height", source = "dimension.height")
    @Mapping(target = "depth", source = "dimension.depth")
    @Mapping(target = "fragile", source = "fragile")
    @Mapping(target = "weight", source = "weight")
    Warehouse toWarehouse(NewProductInWarehouseRequest dto);

}
