package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.persistence.dao.jpa.ProductJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import com.grupo4.VetAndGo.persistence.repository.impl.ProductRepositoryImpl;
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
class ProductRepositoryImplTest {

  @Mock
  private ProductJpaDao productJpaDao;

  @InjectMocks
  private ProductRepositoryImpl productRepository;

  @Test
  void testFindByName_Success() {
    // Arrange
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity entity = new ProductJpaEntity(1L, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100);
    when(productJpaDao.findByName("Dog Food")).thenReturn(Optional.of(entity));

    // Act
    Optional<ProductJpaEntity> result = productRepository.findByName("Dog Food");

    // Assert
    assertTrue(result.isPresent());
    assertEquals("Dog Food", result.get().getName());
    assertEquals(29.99, result.get().getPrice());
    verify(productJpaDao, times(1)).findByName("Dog Food");
  }

  @Test
  void testFindByName_NotFound() {
    // Arrange
    when(productJpaDao.findByName("Unknown Product")).thenReturn(Optional.empty());

    // Act
    Optional<ProductJpaEntity> result = productRepository.findByName("Unknown Product");

    // Assert
    assertFalse(result.isPresent());
    verify(productJpaDao, times(1)).findByName("Unknown Product");
  }

  @Test
  void testExistsByCategoryId_True() {
    // Arrange
    when(productJpaDao.existsByCategoryId(1L)).thenReturn(true);

    // Act
    boolean result = productRepository.existsByCategoryId(1L);

    // Assert
    assertTrue(result);
    verify(productJpaDao, times(1)).existsByCategoryId(1L);
  }

  @Test
  void testExistsByCategoryId_False() {
    // Arrange
    when(productJpaDao.existsByCategoryId(999L)).thenReturn(false);

    // Act
    boolean result = productRepository.existsByCategoryId(999L);

    // Assert
    assertFalse(result);
    verify(productJpaDao, times(1)).existsByCategoryId(999L);
  }

  @Test
  void testFindById_Success() {
    // Arrange
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity entity = new ProductJpaEntity(1L, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100);
    when(productJpaDao.findById(1L)).thenReturn(Optional.of(entity));

    // Act
    Optional<ProductJpaEntity> result = productRepository.findById(1L);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getId());
    assertEquals("Dog Food", result.get().getName());
    verify(productJpaDao, times(1)).findById(1L);
  }

  @Test
  void testFindById_NotFound() {
    // Arrange
    when(productJpaDao.findById(anyLong())).thenReturn(Optional.empty());

    // Act
    Optional<ProductJpaEntity> result = productRepository.findById(999L);

    // Assert
    assertFalse(result.isPresent());
    verify(productJpaDao, times(1)).findById(999L);
  }

  @Test
  void testSave_Insert() {
    // Arrange
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity newEntity = new ProductJpaEntity(null, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100);
    ProductJpaEntity savedEntity = new ProductJpaEntity(1L, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100);
    when(productJpaDao.insert(any(ProductJpaEntity.class))).thenReturn(savedEntity);

    // Act
    ProductJpaEntity result = productRepository.save(newEntity);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Dog Food", result.getName());
    verify(productJpaDao, times(1)).insert(newEntity);
    verify(productJpaDao, never()).update(any());
  }

  @Test
  void testSave_Update() {
    // Arrange
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity existingEntity = new ProductJpaEntity(1L, "Dog Food Premium", categoryEntity, "Premium dog food", 39.99, 150);
    when(productJpaDao.update(any(ProductJpaEntity.class))).thenReturn(existingEntity);

    // Act
    ProductJpaEntity result = productRepository.save(existingEntity);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Dog Food Premium", result.getName());
    assertEquals(39.99, result.getPrice());
    verify(productJpaDao, times(1)).update(existingEntity);
    verify(productJpaDao, never()).insert(any());
  }

  @Test
  void testDeleteById_Success() {
    // Arrange
    doNothing().when(productJpaDao).deleteById(1L);

    // Act
    productRepository.deleteById(1L);

    // Assert
    verify(productJpaDao, times(1)).deleteById(1L);
  }

  @Test
  void testDeleteById_NonExistingId() {
    // Arrange
    doNothing().when(productJpaDao).deleteById(anyLong());

    // Act
    productRepository.deleteById(999L);

    // Assert
    verify(productJpaDao, times(1)).deleteById(999L);
  }

  @Test
  void testGetAll_Success() {
    // Arrange
    int page = 1;
    int size = 10;
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity entity1 = new ProductJpaEntity(1L, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100);
    ProductJpaEntity entity2 = new ProductJpaEntity(2L, "Cat Food", categoryEntity, "Premium cat food", 24.99, 150);
    List<ProductJpaEntity> productList = Arrays.asList(entity1, entity2);

    when(productJpaDao.findAll(page, size)).thenReturn(productList);
    when(productJpaDao.count()).thenReturn(2L);

    // Act
    Page<ProductJpaEntity> result = productRepository.getAll(page, size);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.data().size());
    assertEquals(page, result.pageNumber());
    assertEquals(size, result.pageSize());
    assertEquals(2L, result.totalElements());
    verify(productJpaDao, times(1)).findAll(page, size);
    verify(productJpaDao, times(1)).count();
  }

  @Test
  void testGetAll_EmptyList() {
    // Arrange
    int page = 1;
    int size = 10;
    when(productJpaDao.findAll(page, size)).thenReturn(List.of());
    when(productJpaDao.count()).thenReturn(0L);

    // Act
    Page<ProductJpaEntity> result = productRepository.getAll(page, size);

    // Assert
    assertNotNull(result);
    assertTrue(result.data().isEmpty());
    assertEquals(0L, result.totalElements());
    verify(productJpaDao, times(1)).findAll(page, size);
  }
}
