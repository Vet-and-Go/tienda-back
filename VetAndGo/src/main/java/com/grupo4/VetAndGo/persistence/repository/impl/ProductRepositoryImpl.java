package com.grupo4.VetAndGo.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.ProductJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import com.grupo4.VetAndGo.persistence.repository.mapper.ProductMapper;

public class ProductRepositoryImpl implements ProductRepository {

  @Override
  public Optional<Product> findByName(String name) {
    return productJpaDao.findByName(name)
        .map(ProductMapper.getInstance()::FromProductJpaEntityToProduct);
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
  public Page<Product> getAll(int page, int size, Long categoryId, String sort, String search) {
    List<Product> products = productJpaDao.findAll(page, size, categoryId, sort, search).stream()
        .map(ProductMapper.getInstance()::FromProductJpaEntityToProduct)
        .toList();
    long totalItems = productJpaDao.count();
    return new Page<>(products, page, size, totalItems);
  }

  @Override
  public Optional<Product> findById(Long id) {
    return productJpaDao.findById(id)
        .map(ProductMapper.getInstance()::FromProductJpaEntityToProduct);
  }

  @Override
  public Product save(Product product) {
    ProductJpaEntity entity = ProductMapper.getInstance().FromProductToProductJpaEntity(product);
    ProductJpaEntity savedEntity;
    if (entity.getId() == null) {
      savedEntity = productJpaDao.insert(entity);
    } else {
      savedEntity = productJpaDao.update(entity);
    }
    return ProductMapper.getInstance().FromProductJpaEntityToProduct(savedEntity);
  }
}
