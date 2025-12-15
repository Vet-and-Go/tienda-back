package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;

import java.util.Optional;

public interface CategoryJpaDao extends GenericJpaDao<CategoryJpaEntity> {
    Optional<CategoryJpaEntity> findByName(String nombre);
}
