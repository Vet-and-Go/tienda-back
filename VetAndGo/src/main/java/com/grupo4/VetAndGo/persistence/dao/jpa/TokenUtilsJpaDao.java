package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

public interface TokenUtilsJpaDao {
    String createSessionToken(Long idUser);
    void deletesessionToken(Long idUser);
    UserJpaEntity getUserFromToken(String token);
}
