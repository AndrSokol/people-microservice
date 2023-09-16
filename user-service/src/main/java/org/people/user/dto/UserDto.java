package org.people.user.dto;

import lombok.*;
import org.people.user.external.model.AddressDto;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Builder
@Data
public final class UserDto {
    private UUID uuid;
    private String name;
    private String lastname;
    private AddressDto address;

    public UserDto(UUID uuid, String name, String lastname) {
        this.uuid = uuid;
        this.name = name;
        this.lastname = lastname;
    }

    public UserDto(UUID uuid, String name, String lastname, AddressDto address) {
        this.uuid = uuid;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
    }

    public UUID uuid() {
        return uuid;
    }

    public String name() {
        return name;
    }

    public String lastname() {
        return lastname;
    }

    public AddressDto address() {
        return address;
    }
}
