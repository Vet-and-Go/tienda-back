package com.grupo4.VetAndGo.domain.dto;

import com.grupo4.VetAndGo.domain.model.Role;

public record UserDto(
        Long id,
        String username,
        String password,
        Role role
) {
}
