package org.people.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.people.user.dto.UserCreateRequest;
import org.people.user.dto.UserDto;
import org.people.user.external.AddressService;
import org.people.user.external.model.AddressDto;
import org.people.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final Map<UUID, UserDto> userDtoMap;

    private final AddressService addressService;

    public UserServiceImpl(AddressService addressService) {
        this.addressService = addressService;
        userDtoMap = new HashMap<>();

        UUID uuid = UUID.fromString("51484783-a2dc-4757-b2ea-fada88d5839c");
        userDtoMap.put(uuid, new UserDto(uuid, "Andr", "Sokol"));
    }

    @Override
    public UserDto save(UserCreateRequest userCreateRequest) {
        UserDto userDto = UserDto.builder()
                .uuid(UUID.randomUUID())
                .name(userCreateRequest.name())
                .lastname(userCreateRequest.lastname())
                .build();

        userDtoMap.put(userDto.uuid(), userDto);
        return userDto;
    }

    @Override
    public Optional<UserDto> getByUuid(UUID uuid) {
        UserDto userDto = userDtoMap.get(uuid);
        if (userDto != null) {
            enrichUserWithAddress(userDto);
        }
        return Optional.of(userDto);
    }

    @Override
    public List<UserDto> getAll() {
        return userDtoMap.values().stream().toList();
    }

    private void enrichUserWithAddress(UserDto userDto) {
        log.info("Retrieving address for user with uuid {}", userDto.getUuid());
        try {
            ResponseEntity<AddressDto> addressByUuid = addressService.getAddressByUuid(String.valueOf(userDto.getUuid()));
            if (addressByUuid.getStatusCode().is2xxSuccessful()) {
                userDto.setAddress(addressByUuid.getBody());
            } else {
                log.error("Error during retrieving address for user with uuid: {}", userDto.getUuid());
            }
        } catch (Exception e) {
            log.error("Exception during calling address service: {}", e.getMessage());
        }

    }
}
