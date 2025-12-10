package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<CategoryJpaEntity> findAll();
    Optional<CategoryJpaEntity> findById(Long id);
    CategoryJpaEntity save(CategoryJpaEntity categoria);
    void deleteById(Long id);
    Optional<CategoryJpaEntity> findByName(String name);
}
