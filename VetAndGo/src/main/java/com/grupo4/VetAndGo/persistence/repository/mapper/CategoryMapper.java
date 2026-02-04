package com.grupo4.VetAndGo.persistence.repository.mapper;

import com.grupo4.VetAndGo.domain.model.Category;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;

public class CategoryMapper {

  private static CategoryMapper INSTANCE;

  private CategoryMapper() {
  }

  public static CategoryMapper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CategoryMapper();
    }
    return INSTANCE;
  }

  public CategoryJpaEntity fromCategoryToCategoryJpaEntity(Category category) {
    if (category == null) {
      return null;
    }
    return new CategoryJpaEntity(
        category.getId(),
        category.getName(),
        category.getDescription());
  }

  public Category fromCategoryJpaEntityToCategory(CategoryJpaEntity categoryJpaEntity) {
    if (categoryJpaEntity == null) {
      return null;
    }
    return new Category(
        categoryJpaEntity.getId(),
        categoryJpaEntity.getName(),
        categoryJpaEntity.getDescription());
  }
}
