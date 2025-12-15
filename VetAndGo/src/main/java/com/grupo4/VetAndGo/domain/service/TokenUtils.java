package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.model.User;

public interface TokenUtils {
    User getUserbFromToken(String token);

    void deleteToken(String token);
}
