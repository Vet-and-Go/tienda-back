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
        CategoryJpaEntity entity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");

        // Act
        Category result = CategoryMapper.FromCategoriaEntityJpatoCategoria(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Alimentos", result.getName());
        assertEquals("Comida para mascotas", result.getDescription());
    }

    @Test
    void testFromCategoriaEntityJpatoCategoria_Null() {
        // Act
        Category result = CategoryMapper.FromCategoriaEntityJpatoCategoria(null);

        // Assert
        assertNull(result);
    }

    @Test
    void testFromCategoriaToCategoriaEntityJpa_Success() {
        // Arrange
        Category category = new Category(1L, "Juguetes", "Juguetes para mascotas");

        // Act
        CategoryJpaEntity result = CategoryMapper.FromCategoriaToCategoriaEntityJpa(category);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juguetes", result.getName());
        assertEquals("Juguetes para mascotas", result.getDescription());
    }

    @Test
    void testFromCategoriaToCategoriaEntityJpa_Null() {
        // Act
        CategoryJpaEntity result = CategoryMapper.FromCategoriaToCategoriaEntityJpa(null);

        // Assert
        assertNull(result);
    }

    @Test
    void testFromCategoriaDtoToCategoria_Success() {
        // Arrange
        CategoryDto dto = new CategoryDto(1L, "Medicamentos", "Medicamentos veterinarios");

        // Act
        Category result = CategoryMapper.FromCategoriaDtoToCategoria(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Medicamentos", result.getName());
        assertEquals("Medicamentos veterinarios", result.getDescription());
    }

    @Test
    void testFromCategoriaDtoToCategoria_Null() {
        // Act
        Category result = CategoryMapper.FromCategoriaDtoToCategoria(null);

        // Assert
        assertNull(result);
    }

    @Test
    void testFromCategoriaToCategoriaDto_Success() {
        // Arrange
        Category category = new Category(1L, "Accesorios", "Accesorios para mascotas");

        // Act
        CategoryDto result = CategoryMapper.FromCategoriaToCategoriaDto(category);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Accesorios", result.name());
        assertEquals("Accesorios para mascotas", result.description());
    }

    @Test
    void testFromCategoriaToCategoriaDto_Null() {
        // Act
        CategoryDto result = CategoryMapper.FromCategoriaToCategoriaDto(null);

        // Assert
        assertNull(result);
    }
}
