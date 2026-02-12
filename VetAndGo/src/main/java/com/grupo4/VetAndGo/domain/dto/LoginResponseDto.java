package com.grupo4.VetAndGo.domain.dto;

public record LoginResponseDto(
                String token,
                String username,
                String role,
                Long id) {
}
