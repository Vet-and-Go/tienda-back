package com.grupo4.VetAndGo.controller.webmodel.request.User;

import com.grupo4.VetAndGo.domain.model.Role;

public record UserUpdate(
        Integer id,
        String username,
        String password,
        Role role
) {
}
