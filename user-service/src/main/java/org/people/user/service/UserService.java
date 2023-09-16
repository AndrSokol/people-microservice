package org.people.user.service;

import org.people.user.dto.UserCreateRequest;
import org.people.user.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserDto save(UserCreateRequest userCreateRequest);
    Optional<UserDto> getByUuid(UUID uuid);
    List<UserDto> getAll();
}
