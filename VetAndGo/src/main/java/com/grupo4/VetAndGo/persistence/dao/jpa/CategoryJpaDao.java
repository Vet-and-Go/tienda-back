package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;

import java.util.Optional;
import java.util.List;

public interface CategoryJpaDao extends GenericJpaDao<CategoryJpaEntity> {

  List<CategoryJpaEntity> findAll();

  Optional<CategoryJpaEntity> findByName(String nombre);
}
