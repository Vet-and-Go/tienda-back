package com.grupo4.VetAndGo.persistence.repository.impl;

import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.UserJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaDao userJpaDao;

    public UserRepositoryImpl(UserJpaDao userJpaDao) {
        this.userJpaDao = userJpaDao;
    }

    @Override
    public List<UserJpaEntity> findAll() {
        return userJpaDao.findAll();
    }

    @Override
    public UserJpaEntity findById(Long id) {
        return userJpaDao.findById(id).orElse(null);
    }

    @Override
    public Optional<UserJpaEntity> findByUsername(String username) {
        return userJpaDao.findByUsername(username);
    }

    @Override
    public UserJpaEntity save(UserJpaEntity user) {
        if (user.getId() == null) {
            return userJpaDao.insert(user);
        }
        return userJpaDao.update(user);
    }

    @Override
    public void delete(Long id) {
        userJpaDao.deleteById(id);
    }

}
