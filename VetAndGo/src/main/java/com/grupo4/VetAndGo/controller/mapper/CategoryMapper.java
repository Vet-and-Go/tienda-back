package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.webmodel.response.CategoryResponse;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;

public class CategoryMapper {

    public static CategoryResponse FromCategoryDtoToCategoryResponse(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        return new CategoryResponse(
                categoryDto.id(),
                categoryDto.name(),
                categoryDto.description());
    }
}
