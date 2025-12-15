package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

public interface TokenUtilsRepository {
    String createSessionToken(Long id);
    UserJpaEntity getUserFromToken(String token);
    void deleteToken(Long id);
}
