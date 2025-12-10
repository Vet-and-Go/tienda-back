package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserJpaEntity> findAll();
    UserJpaEntity findById(Integer id);
    Optional<UserJpaEntity> findByUsername(String username);

}
