package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductUpdate;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
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
class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private ProductServiceImpl productServiceImpl;

  @Test
  void testCreate_Success() {
    // Arrange
    ProductInsert productInsert = new ProductInsert("Dog Food", 1L, "Premium dog food", 29.99, 100, "url");
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity savedEntity = new ProductJpaEntity(1L, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100, "url");

    when(productRepository.findByName("Dog Food")).thenReturn(Optional.empty());
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
    when(productRepository.save(any(ProductJpaEntity.class))).thenReturn(savedEntity);

    // Act
    ProductDto result = productServiceImpl.create(productInsert);

    // Assert
    assertNotNull(result);
    assertEquals("Dog Food", result.name());
    assertEquals(29.99, result.price());
    assertEquals(100, result.stock());
    verify(productRepository, times(1)).findByName("Dog Food");
    verify(categoryRepository, times(1)).findById(1L);
    verify(productRepository, times(1)).save(any(ProductJpaEntity.class));
  }

  @Test
  void testCreate_ThrowsExceptionWhenProductNameExists() {
    // Arrange
    ProductInsert productInsert = new ProductInsert("Dog Food", 1L, "Premium dog food", 29.99, 100, "url");
    when(productRepository.findByName("Dog Food")).thenReturn(Optional.of(new ProductJpaEntity()));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      productServiceImpl.create(productInsert);
    });

    assertEquals("Cannot create a product with an existing ID.", exception.getMessage());
    verify(productRepository, times(1)).findByName("Dog Food");
    verify(productRepository, never()).save(any(ProductJpaEntity.class));
  }

  @Test
  void testCreate_ThrowsExceptionWhenCategoryNotFound() {
    // Arrange
    ProductInsert productInsert = new ProductInsert("Dog Food", 999L, "Premium dog food", 29.99, 100, "url");
    when(productRepository.findByName("Dog Food")).thenReturn(Optional.empty());
    when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productServiceImpl.create(productInsert);
    });

    assertEquals("Category not found", exception.getMessage());
    verify(productRepository, times(1)).findByName("Dog Food");
    verify(categoryRepository, times(1)).findById(999L);
  }

  @Test
  void testGetAll_Success() {
    // Arrange
    int page = 1;
    int size = 10;
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity entity1 = new ProductJpaEntity(1L, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100, "url");
    ProductJpaEntity entity2 = new ProductJpaEntity(2L, "Cat Food", categoryEntity, "Premium cat food", 24.99, 150, "url");
    List<ProductJpaEntity> products = Arrays.asList(entity1, entity2);

    when(productRepository.getAll(page, size, null, null, null)).thenReturn(new Page<>(products, page, size, 2L));

    // Act
    Page<ProductDto> result = productServiceImpl.getAll(page, size, null, null, null);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.data().size());
    assertEquals("Dog Food", result.data().get(0).name());
    assertEquals("Cat Food", result.data().get(1).name());
    verify(productRepository, times(1)).getAll(page, size, null, null, null);
  }

  @Test
  void testGetAll_ThrowsExceptionWhenPageInvalid() {
    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      productServiceImpl.getAll(0, 10, null, null, null);
    });

    assertEquals("Invalid page or size", exception.getMessage());
  }

  @Test
  void testGetAll_ThrowsExceptionWhenSizeInvalid() {
    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      productServiceImpl.getAll(1, 0, null, null, null);
    });

    assertEquals("Invalid page or size", exception.getMessage());
  }

  @Test
  void testGetById_Success() {
    // Arrange
    Long id = 1L;
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity entity = new ProductJpaEntity(id, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100, "url");

    when(productRepository.findById(id)).thenReturn(Optional.of(entity));

    // Act
    ProductDto result = productServiceImpl.getById(id);

    // Assert
    assertNotNull(result);
    assertEquals(id, result.id());
    assertEquals("Dog Food", result.name());
    assertEquals(29.99, result.price());
    verify(productRepository, times(1)).findById(id);
  }

  @Test
  void testGetById_ThrowsExceptionWhenNotFound() {
    // Arrange
    Long id = 999L;
    when(productRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productServiceImpl.getById(id);
    });

    assertEquals("Product not found", exception.getMessage());
    verify(productRepository, times(1)).findById(id);
  }

  @Test
  void testFindById_Success() {
    // Arrange
    Long id = 1L;
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity entity = new ProductJpaEntity(id, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100, "url");

    when(productRepository.findById(id)).thenReturn(Optional.of(entity));

    // Act
    Optional<ProductDto> result = productServiceImpl.findById(id);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(id, result.get().id());
    assertEquals("Dog Food", result.get().name());
    verify(productRepository, times(1)).findById(id);
  }

  @Test
  void testFindById_NotFound() {
    // Arrange
    Long id = 999L;
    when(productRepository.findById(id)).thenReturn(Optional.empty());

    // Act
    Optional<ProductDto> result = productServiceImpl.findById(id);

    // Assert
    assertFalse(result.isPresent());
    verify(productRepository, times(1)).findById(id);
  }

  @Test
  void testUpdate_Success() {
    // Arrange
    ProductUpdate productUpdate = new ProductUpdate(1L, "Dog Food Premium", 1L, "Premium dog food", 39.99, 150, "url");
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity existingEntity = new ProductJpaEntity(1L, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100, "url");
    ProductJpaEntity updatedEntity = new ProductJpaEntity(1L, "Dog Food Premium", categoryEntity, "Premium dog food", 39.99, 150, "url");

    when(productRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
    when(productRepository.save(any(ProductJpaEntity.class))).thenReturn(updatedEntity);

    // Act
    ProductDto result = productServiceImpl.update(productUpdate);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.id());
    assertEquals("Dog Food Premium", result.name());
    assertEquals(39.99, result.price());
    assertEquals(150, result.stock());
    verify(productRepository, times(1)).findById(1L);
    verify(categoryRepository, times(1)).findById(1L);
    verify(productRepository, times(1)).save(any(ProductJpaEntity.class));
  }

  @Test
  void testUpdate_ThrowsExceptionWhenProductNotFound() {
    // Arrange
    ProductUpdate productUpdate = new ProductUpdate(999L, "Dog Food", 1L, "Premium dog food", 29.99, 100, "url");
    when(productRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productServiceImpl.update(productUpdate);
    });

    assertEquals("Product not found", exception.getMessage());
    verify(productRepository, times(1)).findById(999L);
    verify(productRepository, never()).save(any(ProductJpaEntity.class));
  }

  @Test
  void testUpdate_ThrowsExceptionWhenCategoryNotFound() {
    // Arrange
    ProductUpdate productUpdate = new ProductUpdate(1L, "Dog Food", 999L, "Premium dog food", 29.99, 100, "url");
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity existingEntity = new ProductJpaEntity(1L, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100, "url");

    when(productRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
    when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productServiceImpl.update(productUpdate);
    });

    assertEquals("Category not found", exception.getMessage());
    verify(productRepository, times(1)).findById(1L);
    verify(categoryRepository, times(1)).findById(999L);
  }

  @Test
  void testDeleteById_Success() {
    // Arrange
    Long id = 1L;
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity entity = new ProductJpaEntity(id, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100, "url");

    when(productRepository.findById(id)).thenReturn(Optional.of(entity));
    doNothing().when(productRepository).deleteById(id);

    // Act
    productServiceImpl.deleteById(id);

    // Assert
    verify(productRepository, times(1)).findById(id);
    verify(productRepository, times(1)).deleteById(id);
  }

  @Test
  void testDeleteById_ThrowsExceptionWhenNotFound() {
    // Arrange
    Long id = 999L;
    when(productRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productServiceImpl.deleteById(id);
    });

    assertEquals("Product not found", exception.getMessage());
    verify(productRepository, times(1)).findById(id);
    verify(productRepository, never()).deleteById(any());
  }
}
