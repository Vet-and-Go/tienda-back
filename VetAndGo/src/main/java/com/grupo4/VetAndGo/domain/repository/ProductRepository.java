package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;

import java.util.Optional;

public interface ProductRepository {
  Page<ProductJpaEntity> getAll(int page, int size);

  Optional<ProductJpaEntity> findById(Long id);

  ProductJpaEntity save(ProductJpaEntity productJpaEntity);

  Optional<ProductJpaEntity> findByName(String name);

  boolean existsByCategoryId(Long categoryId);

  void deleteById(Long id);
}
