package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoriaServiceImpl;

    @Test
    void testGetAll_Success() {
        // Arrange
        CategoryJpaEntity entity1 = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
        CategoryJpaEntity entity2 = new CategoryJpaEntity(2L, "Juguetes", "Juguetes para mascotas");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        // Act
        List<CategoryDto> result = categoriaServiceImpl.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alimentos", result.get(0).name());
        assertEquals("Juguetes", result.get(1).name());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetById_Success() {
        // Arrange
        Long id = 1L;
        CategoryJpaEntity entity = new CategoryJpaEntity(id, "Alimentos", "Comida para mascotas");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        Optional<CategoryDto> result = categoriaServiceImpl.getById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().id());
        assertEquals("Alimentos", result.get().name());
        assertEquals("Comida para mascotas", result.get().description());
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void testGetById_NotFound() {
        // Arrange
        Long id = 999L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<CategoryDto> result = categoriaServiceImpl.getById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void testCreate_Success() {
        // Arrange
        CategoryDto inputDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
        CategoryJpaEntity savedEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        when(categoryRepository.save(any(CategoryJpaEntity.class))).thenReturn(savedEntity);

        // Act
        CategoryDto result = categoriaServiceImpl.create(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Alimentos", result.name());
        assertEquals("Comida para mascotas", result.description());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(CategoryJpaEntity.class));
    }

    @Test
    void testCreate_ThrowsExceptionWhenIdAlreadyExists() {
        // Arrange
        Long existingId = 1L;
        CategoryDto inputDto = new CategoryDto(existingId, "Alimentos", "Comida para mascotas");
        CategoryJpaEntity existingEntity = new CategoryJpaEntity(existingId, "Alimentos Viejos", "Descripción vieja");

        when(categoryRepository.findById(existingId)).thenReturn(Optional.of(existingEntity));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaServiceImpl.create(inputDto);
        });

        assertEquals("La categoría con ID " + existingId + " ya existe.", exception.getMessage());
        verify(categoryRepository, times(1)).findById(existingId);
        verify(categoryRepository, never()).save(any(CategoryJpaEntity.class));
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        Long id = 1L;
        CategoryDto updateDto = new CategoryDto(id, "Alimentos Premium", "Comida premium para mascotas");
        CategoryJpaEntity existingEntity = new CategoryJpaEntity(id, "Alimentos", "Comida para mascotas");
        CategoryJpaEntity updatedEntity = new CategoryJpaEntity(id, "Alimentos Premium", "Comida premium para mascotas");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(categoryRepository.save(any(CategoryJpaEntity.class))).thenReturn(updatedEntity);

        // Act
        CategoryDto result = categoriaServiceImpl.update(id, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("Alimentos Premium", result.name());
        assertEquals("Comida premium para mascotas", result.description());
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).save(any(CategoryJpaEntity.class));
    }

    @Test
    void testUpdate_ThrowsExceptionWhenNotFound() {
        // Arrange
        Long id = 999L;
        CategoryDto updateDto = new CategoryDto(id, "Alimentos", "Descripción");

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaServiceImpl.update(id, updateDto);
        });

        assertEquals("La categoría con ID " + id + " no existe.", exception.getMessage());
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, never()).save(any(CategoryJpaEntity.class));
    }

    @Test
    void testDelete_Success() {
        // Arrange
        Long id = 1L;
        doNothing().when(categoryRepository).deleteById(id);

        // Act
        categoriaServiceImpl.delete(id);

        // Assert
        verify(categoryRepository, times(1)).deleteById(id);
    }
}
