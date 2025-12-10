package com.grupo4.VetAndGo.controller.webmodel.request.User;

public record UserUpdate(
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
