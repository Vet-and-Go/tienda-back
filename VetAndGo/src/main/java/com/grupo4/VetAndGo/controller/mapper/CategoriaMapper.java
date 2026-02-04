package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.webmodel.request.Category.CategoryInsert;
import com.grupo4.VetAndGo.controller.webmodel.response.Categoria.CategoriaDetail;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;

public class CategoriaMapper {
  public static CategoryDto fromCategoryInsertToCategoryDto(CategoryInsert categoryInsert) {
    if (categoryInsert == null) {
      return null;
    }
    return new CategoryDto(
        null,
        categoryInsert.name(),
        categoryInsert.description());
  }

  public static CategoryDto fromCategoryUpdateToCategoryDto(Long id, CategoryInsert categoriaUpdate) {
    if (categoriaUpdate == null) {
      return null;
    }
    return new CategoryDto(
        id,
        categoriaUpdate.name(),
        categoriaUpdate.description());
  }

  public static CategoriaDetail fromCategoryDtoToCategoriaDetail(CategoryDto categoryDto) {
    if (categoryDto == null) {
      return null;
    }
    return new CategoriaDetail(
        categoryDto.id(),
        categoryDto.name(),
        categoryDto.description());
  }

}
