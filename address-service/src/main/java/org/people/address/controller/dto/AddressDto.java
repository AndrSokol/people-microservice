package org.people.address.controller.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AddressDto(UUID uuid, UUID userUuid, String city, String street, Integer building) {
}
