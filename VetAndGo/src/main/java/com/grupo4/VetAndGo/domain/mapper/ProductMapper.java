package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.*;

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

  public ProductDto fromProductToProductDto(Product product) {
    if (product == null) {
      return null;
    }
    return new ProductDto(
        product.getId(),
        product.getName(),
        product.getCategory(),
        product.getDescription(),
        product.getPrice(),
        product.getStock());
  }

  public Product fromProductDtoToProduct(ProductDto productDto) {
    if (productDto == null) {
      return null;
    }
    return new Product(
        productDto.id(),
        productDto.name(),
        productDto.category(),
        productDto.description(),
        productDto.price(),
        productDto.stock());
  }

  public Product fromProductJpaEntityToProduct(ProductJpaEntity productJpaEntity) {
    if (productJpaEntity == null) {
      return null;
    }
    try {
      return new Product(
          productJpaEntity.getId(),
          productJpaEntity.getName(),
          productJpaEntity.getCategory(),
          productJpaEntity.getDescription(),
          productJpaEntity.getPrice(),
          productJpaEntity.getStock());
    } catch (RuntimeException e) {
      return null;
    }
  }

  public ProductJpaEntity fromProductToProductJpaEntity(Product product) {
    if (product == null) {
      return null;
    }
    try {
      return new ProductJpaEntity(
          null,
          product.getName(),
          product.getCategory(),
          product.getDescription(),
          product.getPrice(),
          product.getStock());
    } catch (RuntimeException e) {
      return null;
    }
  }
}
