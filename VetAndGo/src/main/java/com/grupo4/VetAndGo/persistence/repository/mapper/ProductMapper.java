package com.grupo4.VetAndGo.persistence.repository.mapper;

import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;

public class ProductMapper {

  private static ProductMapper INSTANCE;

  private ProductMapper() {
  }

  public static ProductMapper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ProductMapper();
    }
    return INSTANCE;
  }

  public ProductJpaEntity fromProductToProductJpaEntity(Product product) {
    if (product == null) {
      return null;
    }
    return new ProductJpaEntity(
        product.getId(),
        product.getName(),
        CategoryMapper.getInstance().fromCategoryToCategoryJpaEntity(product.getCategory()),
        product.getDescription(),
        product.getBasePrice(),
        product.getStock(),
        product.getDiscountPercentage(),
        product.getFinalPrice(),
        product.getImageUrl());
  }

  public Product fromProductJpaEntityToProduct(ProductJpaEntity productJpaEntity) {
    if (productJpaEntity == null) {
      return null;
    }
    Product product = new Product(
        productJpaEntity.getId(),
        productJpaEntity.getName(),
        CategoryMapper.getInstance().fromCategoryJpaEntityToCategory(productJpaEntity.getCategory()),
        productJpaEntity.getDescription(),
        productJpaEntity.getBasePrice(),
        productJpaEntity.getStock(),
        productJpaEntity.getDiscountPercentage(),
        productJpaEntity.getFinalPrice(),
        productJpaEntity.getImageUrl());
    // Si la entidad tiene finalPrice, lo usamos directamente
    if (productJpaEntity.getFinalPrice() != null) {
      product.setFinalPrice(productJpaEntity.getFinalPrice());
    }
    return product;
  }
}
