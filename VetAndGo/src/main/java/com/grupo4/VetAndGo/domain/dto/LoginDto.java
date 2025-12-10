package com.grupo4.VetAndGo.domain.dto;

public record LoginDto(
        String username,
        String password,
        String role
) {
}
