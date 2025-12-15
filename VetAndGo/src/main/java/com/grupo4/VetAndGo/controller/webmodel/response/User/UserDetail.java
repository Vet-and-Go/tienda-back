package com.grupo4.VetAndGo.controller.webmodel.response.User;

import com.grupo4.VetAndGo.domain.model.Role;

public record UserDetail(
        Long id,
        String name,
        String username,
        Role role
) {
}