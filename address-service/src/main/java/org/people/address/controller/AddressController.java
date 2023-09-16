package org.people.address.controller;


import org.people.address.controller.dto.AddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final Map<UUID, AddressDto> addressDtoMap;

    public AddressController() {
        addressDtoMap = new HashMap<>();

        UUID uuid = UUID.randomUUID();
        UUID userUuid = UUID.fromString("51484783-a2dc-4757-b2ea-fada88d5839c");
        addressDtoMap.put(userUuid, new AddressDto(uuid, userUuid, "London", "Chelsea Road", 1));
    }

    @GetMapping("/user/{uuid}")
    ResponseEntity<AddressDto> getAddressByUuid(@PathVariable String uuid) {
        UUID addressUuid = UUID.fromString(uuid);
        AddressDto addressDto = addressDtoMap.get(addressUuid);
        if (addressDto != null) {
            return new ResponseEntity<>(addressDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
