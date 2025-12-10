package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.persistence.dao.jpa.CategoryJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import com.grupo4.VetAndGo.persistence.repository.impl.CategoryRepositoryImpl;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryImplTest {

    @Mock
    private CategoryJpaDao categoryJpaDao;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepository;

    @Test
    void testFindAll_Success() {
        // Arrange
        CategoryJpaEntity entity1 = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
        CategoryJpaEntity entity2 = new CategoryJpaEntity(2L, "Juguetes", "Juguetes para mascotas");
        List<CategoryJpaEntity> expectedList = Arrays.asList(entity1, entity2);
        when(categoryJpaDao.findAll()).thenReturn(expectedList);

        // Act
        List<CategoryJpaEntity> result = categoryRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alimentos", result.get(0).getName());
        assertEquals("Juguetes", result.get(1).getName());
        verify(categoryJpaDao, times(1)).findAll();
    }

    @Test
    void testFindAll_EmptyList() {
        // Arrange
        when(categoryJpaDao.findAll()).thenReturn(List.of());

        // Act
        List<CategoryJpaEntity> result = categoryRepository.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(categoryJpaDao, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        // Arrange
        CategoryJpaEntity entity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
        when(categoryJpaDao.findById(1L)).thenReturn(Optional.of(entity));

        // Act
        Optional<CategoryJpaEntity> result = categoryRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Alimentos", result.get().getName());
        verify(categoryJpaDao, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(categoryJpaDao.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<CategoryJpaEntity> result = categoryRepository.findById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryJpaDao, times(1)).findById(999L);
    }

    @Test
    void testSave_Insert() {
        // Arrange
        CategoryJpaEntity newEntity = new CategoryJpaEntity(null, "Medicamentos", "Productos veterinarios");
        CategoryJpaEntity savedEntity = new CategoryJpaEntity(1L, "Medicamentos", "Productos veterinarios");
        when(categoryJpaDao.insert(any(CategoryJpaEntity.class))).thenReturn(savedEntity);

        // Act
        CategoryJpaEntity result = categoryRepository.save(newEntity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Medicamentos", result.getName());
        verify(categoryJpaDao, times(1)).insert(newEntity);
        verify(categoryJpaDao, never()).update(any());
    }

    @Test
    void testSave_Update() {
        // Arrange
        CategoryJpaEntity existingEntity = new CategoryJpaEntity(1L, "Alimentos Premium", "Comida premium");
        when(categoryJpaDao.update(any(CategoryJpaEntity.class))).thenReturn(existingEntity);

        // Act
        CategoryJpaEntity result = categoryRepository.save(existingEntity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Alimentos Premium", result.getName());
        verify(categoryJpaDao, times(1)).update(existingEntity);
        verify(categoryJpaDao, never()).insert(any());
    }

    @Test
    void testDeleteById_Success() {
        // Arrange
        doNothing().when(categoryJpaDao).deleteById(1L);

        // Act
        categoryRepository.deleteById(1L);

        // Assert
        verify(categoryJpaDao, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NonExistingId() {
        // Arrange
        doNothing().when(categoryJpaDao).deleteById(anyLong());

        // Act
        categoryRepository.deleteById(999L);

        // Assert
        verify(categoryJpaDao, times(1)).deleteById(999L);
    }
}
