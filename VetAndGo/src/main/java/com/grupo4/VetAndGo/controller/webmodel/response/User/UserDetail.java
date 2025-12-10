package com.grupo4.VetAndGo.controller.webmodel.response.User;

public record UserDetail(
        Integer id,
        String name,
        String username,
        String email,
        int phone,
        String address,
        String birthDate,
        String country,
        String role
) {
}