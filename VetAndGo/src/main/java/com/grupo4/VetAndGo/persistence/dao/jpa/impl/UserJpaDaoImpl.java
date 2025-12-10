package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.UserJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class UserJpaDaoImpl implements UserJpaDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<UserJpaEntity> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public List<UserJpaEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<UserJpaEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserJpaEntity insert(UserJpaEntity jpaEntity) {
        return null;
    }

    @Override
    public UserJpaEntity update(UserJpaEntity jpaEntity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public long count() {
        return 0;
    }


}
