package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

import java.util.Optional;
import java.util.List;

public interface UserJpaDao extends GenericJpaDao<UserJpaEntity> {

  List<UserJpaEntity> findAll();

  Optional<UserJpaEntity> findByUsername(String username);
}
