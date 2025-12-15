package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.*;

import java.util.Optional;
import java.util.List;

public interface ProductJpaDao extends GenericJpaDao<ProductJpaEntity> {

  // Optional<ProductJpaEntity> findById(Long id);

  List<ProductJpaEntity> findAll(int page, int size);

  Optional<ProductJpaEntity> findByName(String name);

  boolean existsByCategoryId(Long categoryId);

  void deleteById(Long id);
}
