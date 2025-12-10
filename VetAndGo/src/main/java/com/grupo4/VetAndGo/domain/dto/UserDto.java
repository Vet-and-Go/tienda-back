package com.grupo4.VetAndGo.domain.dto;

public record UserDto(
        Integer id,
        String name,
        String username,
        String email,
        String password,
        int phone,
        String address,
        String birthDate,
        String country,
        String role
) {
}
