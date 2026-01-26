package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {
  Page<Product> getAll(int page, int size, Long categoryId, String sort, String search);

  Optional<Product> findById(Long id);

  Product save(Product product);

  Optional<Product> findByName(String name);

  boolean existsByCategoryId(Long categoryId);

  void deleteById(Long id);
}
