package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.TestConfig;
import com.grupo4.VetAndGo.persistence.dao.jpa.ProductJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProductJpaDaoImplTest {

  @Autowired
  private ProductJpaDao productJpaDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  void testFindByName_Success() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    ProductJpaEntity product = new ProductJpaEntity(null, "Dog Food", category, "Premium dog food", new BigDecimal("29.99"), 100, new BigDecimal("0.00"), new BigDecimal("29.99"), "url");
    entityManager.persist(product);
    entityManager.flush();

    // Act
    Optional<ProductJpaEntity> result = productJpaDao.findByName("Dog Food");

    // Assert
    assertTrue(result.isPresent());
    assertEquals("Dog Food", result.get().getName());
    assertEquals(new BigDecimal("29.99"), result.get().getBasePrice());
  }

  @Test
  void testFindByName_NotFound() {
    // Act
    Optional<ProductJpaEntity> result = productJpaDao.findByName("Unknown Product");

    // Assert
    assertFalse(result.isPresent());
  }

  @Test
  void testFindAll_Success() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    ProductJpaEntity product1 = new ProductJpaEntity(null, "Dog Food", category, "Premium dog food", new BigDecimal("29.99"), 100, new BigDecimal("0.00"), new BigDecimal("29.99"), "url");
    ProductJpaEntity product2 = new ProductJpaEntity(null, "Cat Food", category, "Premium cat food", new BigDecimal("24.99"), 150, new BigDecimal("0.00"), new BigDecimal("24.99"), "url");
    entityManager.persist(product1);
    entityManager.persist(product2);
    entityManager.flush();

    // Act
    List<ProductJpaEntity> result = productJpaDao.findAll(1, 10, null, null, null);

    // Assert
    assertEquals(2, result.size());
  }

  @Test
  void testFindById_Success() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    ProductJpaEntity product = new ProductJpaEntity(null, "Dog Food", category, "Premium dog food", new BigDecimal("29.99"), 100, new BigDecimal("0.00"), new BigDecimal("29.99"), "url");
    entityManager.persist(product);
    entityManager.flush();

    // Act
    Optional<ProductJpaEntity> result = productJpaDao.findById(product.getId());

    // Assert
    assertTrue(result.isPresent());
    assertEquals(product.getId(), result.get().getId());
    assertEquals("Dog Food", result.get().getName());
  }

  @Test
  void testInsert_Success() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    ProductJpaEntity newProduct = new ProductJpaEntity(null, "Dog Food", category, "Premium dog food", new BigDecimal("29.99"), 100, new BigDecimal("0.00"), new BigDecimal("29.99"), "url");

    // Act
    productJpaDao.insert(newProduct);

    // Assert
    assertThat(newProduct.getId()).isNotNull();
    assertThat(newProduct.getId()).isGreaterThan(0);
  }

  @Test
  void testUpdate_Success() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    ProductJpaEntity product = new ProductJpaEntity(null, "Dog Food", category, "Premium dog food", new BigDecimal("29.99"), 100, new BigDecimal("0.00"), new BigDecimal("29.99"), "url");
    entityManager.persist(product);
    entityManager.flush();

    product.setName("Dog Food Premium");
    product.setBasePrice(new BigDecimal("39.99"));
    product.setFinalPrice(new BigDecimal("39.99"));

    // Act
    ProductJpaEntity result = productJpaDao.update(product);
    entityManager.flush();

    // Assert
    assertEquals("Dog Food Premium", result.getName());
    assertEquals(new BigDecimal("39.99"), result.getBasePrice());
  }

  @Test
  void testDeleteById_Success() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    ProductJpaEntity product = new ProductJpaEntity(null, "Dog Food", category, "Premium dog food", new BigDecimal("29.99"), 100, new BigDecimal("0.00"), new BigDecimal("29.99"), "url");
    entityManager.persist(product);
    entityManager.flush();
    Long productId = product.getId();

    // Act
    productJpaDao.deleteById(productId);
    entityManager.flush();

    // Assert
    Optional<ProductJpaEntity> result = productJpaDao.findById(productId);
    assertFalse(result.isPresent());
  }

  @Test
  void testCount_Success() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    ProductJpaEntity product1 = new ProductJpaEntity(null, "Dog Food", category, "Premium dog food", new BigDecimal("29.99"), 100, new BigDecimal("0.00"), new BigDecimal("29.99"), "url");
    ProductJpaEntity product2 = new ProductJpaEntity(null, "Cat Food", category, "Premium cat food", new BigDecimal("24.99"), 150, new BigDecimal("0.00"), new BigDecimal("24.99"), "url");
    entityManager.persist(product1);
    entityManager.persist(product2);
    entityManager.flush();

    // Act
    long count = productJpaDao.count();

    // Assert
    assertEquals(2, count);
  }

  @Test
  void testFindAll_WithPagination_Success() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    for (int i = 1; i <= 15; i++) {
      ProductJpaEntity product = new ProductJpaEntity(null, "Product " + i, category, "Description " + i, new BigDecimal(String.valueOf(10.0 * i)), 100 + i, new BigDecimal("0.00"), new BigDecimal(String.valueOf(10.0 * i)), "url");
      entityManager.persist(product);
    }
    entityManager.flush();

    // Act
    List<ProductJpaEntity> result = productJpaDao.findAll(1, 10, null, null, null);

    // Assert
    assertEquals(10, result.size());
  }

  @Test
  void testFindAll_WithPagination_SecondPage() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    for (int i = 1; i <= 15; i++) {
      ProductJpaEntity product = new ProductJpaEntity(null, "Product " + i, category, "Description " + i, new BigDecimal(String.valueOf(10.0 * i)), 100 + i, new BigDecimal("0.00"), new BigDecimal(String.valueOf(10.0 * i)), "url");
      entityManager.persist(product);
    }
    entityManager.flush();

    // Act
    List<ProductJpaEntity> result = productJpaDao.findAll(2, 10, null, null, null);

    // Assert
    assertEquals(5, result.size());
  }

  @Test
  void testUpdate_ThrowsExceptionWhenProductNotFound() {
    // Arrange
    CategoryJpaEntity category = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
    entityManager.persist(category);
    entityManager.flush();

    ProductJpaEntity product = new ProductJpaEntity(999L, "Non-Existent", category, "Description", new BigDecimal("10.0"), 100, new BigDecimal("0.00"), new BigDecimal("10.0"), "url");

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      productJpaDao.update(product);
    });

    assertThat(exception.getMessage()).contains("Product with id 999 not found");
  }
}
