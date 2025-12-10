package com.grupo4.VetAndGo.controller.webmodel.request.login;

public record LoginRequest(
        String username,
        String password,
        String role
) {
}
