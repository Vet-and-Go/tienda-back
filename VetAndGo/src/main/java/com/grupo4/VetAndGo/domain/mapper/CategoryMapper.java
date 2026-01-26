package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.model.Category;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;

public class CategoryMapper {
    public static Category FromCategoryJpaEntityToCategory(CategoryJpaEntity categoryJpaEntity) {
        if (categoryJpaEntity == null) {
            return null;
        }
        return  new Category(
            categoryJpaEntity.getId(),
            categoryJpaEntity.getName(),
            categoryJpaEntity.getDescription()
        );

    }
    public static CategoryJpaEntity FromCategoryToCategoryJpaEntity(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryJpaEntity(
            category.getId(),
            category.getName(),
            category.getDescription()
        );
    }
    public static Category FromCategoryDtoToCategory(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        return new Category(
            categoryDto.id(),
            categoryDto.name(),
            categoryDto.description()
        );
    }
    public static CategoryDto FromCategoryToCategoryDto(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDto(
            category.getId(),
            category.getName(),
            category.getDescription()
        );
    }


}
