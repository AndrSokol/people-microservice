package org.people.user.controller;


import org.people.user.dto.UserCreateRequest;
import org.people.user.dto.UserDto;
import org.people.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<UserDto> createNewUser(UserCreateRequest userCreateRequest) {
        UserDto userDto = userService.save(userCreateRequest);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    ResponseEntity<UserDto> getUserByUuid(@PathVariable String uuid) {
        UUID userUuid = UUID.fromString(uuid);
        Optional<UserDto> userDtoOptional = userService.getByUuid(userUuid);
        return userDtoOptional.map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    ResponseEntity<Collection<UserDto>> getUsers() {
        List<UserDto> usersList = userService.getAll();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

}
