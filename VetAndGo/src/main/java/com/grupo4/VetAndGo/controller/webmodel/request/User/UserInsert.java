package com.grupo4.VetAndGo.controller.webmodel.request.User;

public record UserInsert(
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
