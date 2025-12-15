package com.grupo4.VetAndGo.controller.webmodel.request.User;

import com.grupo4.VetAndGo.domain.model.Role;

public record UserInsert(
        String username,
        String password,
        Role role
) {
}
