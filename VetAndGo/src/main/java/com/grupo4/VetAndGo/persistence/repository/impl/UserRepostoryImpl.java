package com.grupo4.VetAndGo.persistence.repository.impl;

import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

import java.util.List;
import java.util.Optional;

public class UserRepostoryImpl implements UserRepository {

    @Override
    public List<UserJpaEntity> findAll() {
        return List.of();
    }

    @Override
    public UserJpaEntity findById(Integer id) {
        return null;
    }

    @Override
    public Optional<UserJpaEntity> findByUsername(String username) {
        return Optional.empty();
    }
}
