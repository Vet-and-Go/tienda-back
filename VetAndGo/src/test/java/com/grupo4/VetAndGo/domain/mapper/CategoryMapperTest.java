package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.model.Category;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    void testFromCategoriaEntityJpatoCategoria_Success() {
        // Arrange
        CategoryJpaEntity entity = new CategoryJpaEntity(1L, "Test Name", "Test Description");

        // Act
        Category result = CategoryMapper.FromCategoriaEntityJpatoCategoria(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Name", result.getName());
        assertEquals("Test Description", result.getDescription());
    }

    @Test
    void testFromCategoriaEntityJpatoCategoria_Null() {
        assertNull(CategoryMapper.FromCategoriaEntityJpatoCategoria(null));
    }

    @Test
    void testFromCategoriaToCategoriaEntityJpa_Success() {
        // Arrange
        Category category = new Category(1L, "Test Name", "Test Description");

        // Act
        CategoryJpaEntity result = CategoryMapper.FromCategoriaToCategoriaEntityJpa(category);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Name", result.getName());
        assertEquals("Test Description", result.getDescription());
    }

    @Test
    void testFromCategoriaToCategoriaEntityJpa_Null() {
        assertNull(CategoryMapper.FromCategoriaToCategoriaEntityJpa(null));
    }

    @Test
    void testFromCategoriaDtoToCategoria_Success() {
        // Arrange
        CategoryDto dto = new CategoryDto(1L, "Test Name", "Test Description");

        // Act
        Category result = CategoryMapper.FromCategoriaDtoToCategoria(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Name", result.getName());
        assertEquals("Test Description", result.getDescription());
    }

    @Test
    void testFromCategoriaDtoToCategoria_Null() {
        assertNull(CategoryMapper.FromCategoriaDtoToCategoria(null));
    }

    @Test
    void testFromCategoriaToCategoriaDto_Success() {
        // Arrange
        Category category = new Category(1L, "Test Name", "Test Description");

        // Act
        CategoryDto result = CategoryMapper.FromCategoriaToCategoriaDto(category);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Name", result.name());
        assertEquals("Test Description", result.description());
    }

    @Test
    void testFromCategoriaToCategoriaDto_Null() {
        assertNull(CategoryMapper.FromCategoriaToCategoriaDto(null));
    }
}
