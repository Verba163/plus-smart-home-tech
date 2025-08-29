package ru.yandex.practicum.service.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.repository.ReservationRepository;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.warehouse.model.dto.AddressDto;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressProviderImpl implements AddressProvider {
    private final WarehouseRepository itemRepository;
    private final ReservationRepository reservationRepository;

    private static final String[] ADDRESSES = new String[]{"ADDRESS_1", "ADDRESS_2"};
    private static final SecureRandom RANDOM = new SecureRandom();


    @Override
    public AddressDto getWarehouseAddress() {
        String address = ADDRESSES[RANDOM.nextInt(ADDRESSES.length)];
        log.info("Received request for warehouse address. Providing: {}", address);
        return AddressDto.builder()
                .country(address)
                .city(address)
                .street(address)
                .house(address)
                .flat(address)
                .build();
    }
}
