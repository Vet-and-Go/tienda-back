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

  public ProductDto FromProductToProductDto(Product product) {
    if (product == null) {
      return null;
    }
    return new ProductDto(
        product.getId(),
        product.getName(),
        CategoryMapper.FromCategoryToCategoryDto(product.getCategory()),
        product.getDescription(),
        product.getBasePrice(),
        product.getStock(),
        product.getDiscountPercentage(),
        product.getFinalPrice(),
        product.getImageUrl());
  }

  public Product FromProductDtoToProduct(ProductDto productDto) {
    if (productDto == null) {
      return null;
    }
    return new Product(
        productDto.id(),
        productDto.name(),
        CategoryMapper.FromCategoryDtoToCategory(productDto.category()),
        productDto.description(),
        productDto.basePrice(),
        productDto.stock(),
        productDto.discountPercentage(),
        productDto.finalPrice(),
        productDto.imageUrl());
  }

  public Product FromProductJpaEntityToProduct(ProductJpaEntity productJpaEntity) {
    if (productJpaEntity == null) {
      return null;
    }
    try {
      Product product = new Product(
          productJpaEntity.getId(),
          productJpaEntity.getName(),
          CategoryMapper.FromCategoryJpaEntityToCategory(productJpaEntity.getCategory()),
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
    } catch (RuntimeException e) {
      return null;
    }
  }

  public ProductJpaEntity FromProductToProductJpaEntity(Product product) {
    if (product == null) {
      return null;
    }
    try {
      return new ProductJpaEntity(
          product.getId(),
          product.getName(),
          CategoryMapper.FromCategoryToCategoryJpaEntity(product.getCategory()),
          product.getDescription(),
          product.getBasePrice(),
          product.getStock(),
          product.getDiscountPercentage(),
          product.getFinalPrice(),
          product.getImageUrl());
    } catch (RuntimeException e) {
      return null;
    }
  }
}
