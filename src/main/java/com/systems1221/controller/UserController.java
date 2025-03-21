package com.systems1221.controller;

import com.systems1221.dto.UserDto;
import com.systems1221.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Добавление нового пользователя")
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о пользователе по ID")
    public UserDto getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных пользователя по ID")
    public void updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя по ID")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    @Operation(summary = "Получение списка всех пользователей")
    public Collection<UserDto> getAllUsers() {
        return userService.findAllUsers();
    }


}
