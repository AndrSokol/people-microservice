package org.people.address.controller.dto;

import java.util.UUID;

public record AddressCreateRequest(UUID userUuid, String city, String street, Integer building) {
}
