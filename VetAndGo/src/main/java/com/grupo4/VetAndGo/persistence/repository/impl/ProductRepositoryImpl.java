package com.grupo4.VetAndGo.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.ProductJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;

public class ProductRepositoryImpl implements ProductRepository {

  @Override
  public Optional<ProductJpaEntity> findByName(String name) {
    return productJpaDao.findByName(name);
  }

  @Override
  public boolean existsByCategoryId(Long categoryId) {
    return productJpaDao.existsByCategoryId(categoryId);
  }

  private final ProductJpaDao productJpaDao;

  public ProductRepositoryImpl(ProductJpaDao productJpaDao) {
    this.productJpaDao = productJpaDao;
  }

  @Override
  public void deleteById(Long id) {
    productJpaDao.deleteById(id);
  }

  @Override
  public Page<ProductJpaEntity> getAll(int page, int size) {
    List<ProductJpaEntity> products = productJpaDao.findAll(page, size).stream().toList();
    long totalItems = productJpaDao.count();
    return new Page<>(products, page, size, totalItems);
  }

  @Override
  public Optional<ProductJpaEntity> findById(Long id) {
    return productJpaDao.findById(id);
  }

  @Override
  public ProductJpaEntity save(ProductJpaEntity productJpaEntity) {
    if (productJpaEntity.getId() == null) {
      return productJpaDao.insert(productJpaEntity);
    } else {
      return productJpaDao.update(productJpaEntity);
    }
  }
}
