package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.exception.BussinesException;
import com.grupo4.VetAndGo.domain.exception.ResourceNotFoundException;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryJpaEntity categoryEntity;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        categoryEntity = new CategoryJpaEntity(1L, "Category1", "Description1");
        categoryDto = new CategoryDto(1L, "Category1", "Description1");
    }

    @Test
    void getAll_ShouldReturnListOfCategories_WhenCategoriesExist() {
        when(categoryRepository.findAll()).thenReturn(List.of(categoryEntity));

        List<CategoryDto> result = categoryService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(categoryEntity.getName(), result.get(0).name());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAll_ShouldThrowResourceNotFoundException_WhenNoCategoriesExist() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getAll());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getById_ShouldReturnCategory_WhenCategoryExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));

        Optional<CategoryDto> result = categoryService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(categoryEntity.getName(), result.get().name());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException_WhenCategoryDoesNotExist() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getById(1L));
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void create_ShouldReturnCategory_WhenCategoryDoesNotExist() {
        when(categoryRepository.findByName(categoryDto.name())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(CategoryJpaEntity.class))).thenReturn(categoryEntity);

        CategoryDto result = categoryService.create(categoryDto);

        assertNotNull(result);
        assertEquals(categoryDto.name(), result.name());
        verify(categoryRepository, times(1)).findByName(categoryDto.name());
        verify(categoryRepository, times(1)).save(any(CategoryJpaEntity.class));
    }

    @Test
    void create_ShouldThrowBusinessException_WhenCategoryAlreadyExists() {
        when(categoryRepository.findByName(categoryDto.name())).thenReturn(Optional.of(categoryEntity));

        assertThrows(BussinesException.class, () -> categoryService.create(categoryDto));
        verify(categoryRepository, times(1)).findByName(categoryDto.name());
        verify(categoryRepository, never()).save(any(CategoryJpaEntity.class));
    }

    @Test
    void update_ShouldReturnUpdatedCategory_WhenCategoryExists() {
        CategoryDto updatedDto = new CategoryDto(1L, "UpdatedName", "UpdatedDesc");
        CategoryJpaEntity updatedEntity = new CategoryJpaEntity(1L, "UpdatedName", "UpdatedDesc");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        when(categoryRepository.save(any(CategoryJpaEntity.class))).thenReturn(updatedEntity);

        CategoryDto result = categoryService.update(1L, updatedDto);

        assertNotNull(result);
        assertEquals(updatedDto.name(), result.name());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(CategoryJpaEntity.class));
    }

    @Test
    void update_ShouldThrowResourceNotFoundException_WhenCategoryDoesNotExist() {
        CategoryDto updatedDto = new CategoryDto(1L, "UpdatedName", "UpdatedDesc");
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.update(1L, updatedDto));
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, never()).save(any(CategoryJpaEntity.class));
    }

    @Test
    void delete_ShouldDeleteCategory_WhenCategoryExistsAndNotAssociatedWithProducts() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        when(productRepository.existsByCategoryId(1L)).thenReturn(false);

        categoryService.delete(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).existsByCategoryId(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void delete_ShouldThrowResourceNotFoundException_WhenCategoryDoesNotExist() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Note: The implementation of delete checks findById first
        assertThrows(ResourceNotFoundException.class, () -> categoryService.delete(1L));
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, never()).existsByCategoryId(anyLong());
        verify(categoryRepository, never()).deleteById(anyLong());

    }

    @Test
    void delete_ShouldThrowBusinessException_WhenCategoryIsAssociatedWithProducts() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        when(productRepository.existsByCategoryId(1L)).thenReturn(true);

        assertThrows(BussinesException.class, () -> categoryService.delete(1L));
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).existsByCategoryId(1L);
        verify(categoryRepository, never()).deleteById(1L);
    }
}
