package ru.yandex.practicum.service.calculator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.DeliveryModel;
import ru.yandex.practicum.order.model.dto.OrderModelDto;
import ru.yandex.practicum.warehouse.model.dto.AddressDto;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DeliveryCostCalculatorImpl implements DeliveryCostCalculator {

    @Value("${delivery.base_rate}")
    private BigDecimal baseRate;

    @Value("${delivery.address_rate}")
    private BigDecimal addressRate;

    @Value("${delivery.fragile_rate}")
    private BigDecimal fragileRate;

    @Value("${delivery.weight_rate}")
    private BigDecimal weightRate;

    @Value("${delivery.volume_rate}")
    private BigDecimal volumeRate;

    @Value("${delivery.street_rate}")
    private BigDecimal streetRate;

    @Override
    public BigDecimal calculate(OrderModelDto orderModelDto, DeliveryModel delivery, AddressDto warehouseAddress) {
        BigDecimal deliveryCost = baseRate;

        String warehouseStreet = warehouseAddress.getStreet();
        String deliveryStreet = delivery.getToAddress().getStreet();

        if ("ADDRESS_1".equals(warehouseStreet)) {
            deliveryCost = deliveryCost.multiply(BigDecimal.valueOf(2));
        } else if ("ADDRESS_2".equals(warehouseStreet)) {
            deliveryCost = deliveryCost.add(deliveryCost.multiply(addressRate));
        }

        if (orderModelDto.isFragile()) {
            deliveryCost = deliveryCost.add(deliveryCost.multiply(fragileRate));
        }

        BigDecimal weight = BigDecimal.valueOf(orderModelDto.getDeliveryWeight());
        BigDecimal volume = BigDecimal.valueOf(orderModelDto.getDeliveryVolume());

        deliveryCost = deliveryCost.add(weight.multiply(weightRate));
        deliveryCost = deliveryCost.add(volume.multiply(volumeRate));

        if (deliveryStreet.equals(warehouseStreet)) {
            deliveryCost = deliveryCost.add(deliveryCost.multiply(streetRate));
        }

        return deliveryCost;
    }
}