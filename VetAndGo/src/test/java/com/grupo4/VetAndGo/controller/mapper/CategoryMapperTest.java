package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.webmodel.response.CategoryResponse;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    void testFromCategoryDtoToCategoryResponse_Success() {
        CategoryDto dto = new CategoryDto(1L, "Alimentos", "Comida");
        CategoryResponse response = CategoryMapper.fromCategoryDtoToCategoryResponse(dto);

        assertNotNull(response);
        assertEquals(dto.id(), response.id());
        assertEquals(dto.name(), response.name());
        assertEquals(dto.description(), response.description());
    }

    @Test
    void testFromCategoryDtoToCategoryResponse_Null() {
        assertNull(CategoryMapper.fromCategoryDtoToCategoryResponse(null));
    }
}
