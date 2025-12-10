package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

import java.util.Optional;

public interface UserJpaDao extends GenericJpaDao<UserJpaEntity> {
    Optional<UserJpaEntity> findByUsername(String username);
}
