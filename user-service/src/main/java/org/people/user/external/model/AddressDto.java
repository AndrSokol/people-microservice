package org.people.user.external.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AddressDto(UUID uuid, UUID userUuid, String city, String street, Integer building) {
}
