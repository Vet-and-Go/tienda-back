package com.grupo4.VetAndGo.controller;

import com.grupo4.VetAndGo.controller.webmodel.request.User.UserInsert;
import com.grupo4.VetAndGo.controller.webmodel.response.User.UserOverview;
import com.grupo4.VetAndGo.domain.dto.LoginDto;
import com.grupo4.VetAndGo.domain.dto.LoginResponseDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.domain.service.TokenUtils;
import com.grupo4.VetAndGo.domain.service.UserService;
import com.grupo4.VetAndGo.spring.annotation.RequireAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final TokenUtils tokenUtils;

    public UserController(UserService userService, TokenUtils tokenUtils) {
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }

    @RequireAdmin
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @RequireAdmin
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @RequireAdmin
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserInsert userInsert) {
        UserDto userToCreate = new UserDto(null, userInsert.username(), userInsert.password(), Role.USER);
        UserDto createdUser = userService.create(userToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @RequireAdmin
    @PostMapping
    public ResponseEntity<UserDto> createUserAsAdmin(@RequestBody UserInsert userInsert) {
        UserDto userToCreate = new UserDto(null, userInsert.username(), userInsert.password(), userInsert.role());
        UserDto createdUser = userService.create(userToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @RequireAdmin
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(id, userDto));
    }

    @RequireAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/login/{role}")
    public ResponseEntity<LoginResponseDto> login(@PathVariable String role, @RequestBody LoginDto loginDto) {
        UserDto user = userService.findByUsername(loginDto.username());
        if (role.equals("admin") && user.role() != Role.ADMIN) {
            throw new ValidationException(
                    "Usuario no autorizado para acceder como administrador.");
        }

        String token = userService.login(loginDto);
        LoginResponseDto response = new LoginResponseDto(token, user.username(), user.role().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/session")
    public ResponseEntity<UserDto> validateSession(@RequestBody String token) {
        User user = tokenUtils.validateToken(token);
        if (user == null) {
            throw new ValidationException(
                    "Token inválido o expirado.");
        }
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestBody LoginDto loginDto) {
        userService.logout(loginDto);
        return ResponseEntity.noContent().build();
    }
}