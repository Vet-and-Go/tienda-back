package com.grupo4.VetAndGo.controller;

import com.grupo4.VetAndGo.controller.webmodel.request.User.UserInsert;
import com.grupo4.VetAndGo.domain.dto.LoginDto;
import com.grupo4.VetAndGo.domain.dto.LoginResponseDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.service.UserService;
import com.grupo4.VetAndGo.spring.annotation.RequireAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequireAdmin
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @RequireAdmin
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @RequireAdmin
    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserInsert userInsert) {
        UserDto userToCreate = new UserDto(null, userInsert.username(), userInsert.password(), Role.USER);
        UserDto createdUser = userService.create(userToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @RequireAdmin
    @PostMapping("/admin/users")
    public ResponseEntity<UserDto> createUserAsAdmin(@RequestBody UserInsert userInsert) {
        UserDto userToCreate = new UserDto(null, userInsert.username(), userInsert.password(), userInsert.role());
        UserDto createdUser = userService.create(userToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @RequireAdmin
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(id, userDto));
    }

    @RequireAdmin
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        String token = userService.login(loginDto);
        UserDto user = userService.findByUsername(loginDto.username());
        LoginResponseDto response = new LoginResponseDto(token, user.username(), user.role().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestBody LoginDto loginDto) {
        userService.logout(loginDto);
        return ResponseEntity.noContent().build();
    }
}
