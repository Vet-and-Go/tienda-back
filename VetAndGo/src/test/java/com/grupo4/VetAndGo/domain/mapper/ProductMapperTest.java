package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.model.Category;
import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

  @Test
  void testFromProductJpaEntityToProduct_Success() {
    // Arrange
    CategoryJpaEntity categoryEntity = new CategoryJpaEntity(1L, "Alimentos", "Comida para mascotas");
    ProductJpaEntity entity = new ProductJpaEntity(1L, "Dog Food", categoryEntity, "Premium dog food", 29.99, 100);

    // Act
    Product result = ProductMapper.getInstance().fromProductJpaEntityToProduct(entity);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Dog Food", result.getName());
    assertEquals("Premium dog food", result.getDescription());
    assertEquals(29.99, result.getPrice());
    assertEquals(100, result.getStock());
    assertNotNull(result.getCategory());
    assertEquals(1L, result.getCategory().getId());
  }

  @Test
  void testFromProductJpaEntityToProduct_Null() {
    // Act
    Product result = ProductMapper.getInstance().fromProductJpaEntityToProduct(null);

    // Assert
    assertNull(result);
  }

  @Test
  void testFromProductToProductJpaEntity_Success() {
    // Arrange
    Category category = new Category(1L, "Juguetes", "Juguetes para mascotas");
    Product product = new Product(1L, "Cat Toy", category, "Fun cat toy", 9.99, 50);

    // Act
    ProductJpaEntity result = ProductMapper.getInstance().fromProductToProductJpaEntity(product);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Cat Toy", result.getName());
    assertEquals("Fun cat toy", result.getDescription());
    assertEquals(9.99, result.getPrice());
    assertEquals(50, result.getStock());
    assertNotNull(result.getCategory());
  }

  @Test
  void testFromProductToProductJpaEntity_Null() {
    // Act
    ProductJpaEntity result = ProductMapper.getInstance().fromProductToProductJpaEntity(null);

    // Assert
    assertNull(result);
  }

  @Test
  void testFromProductDtoToProduct_Success() {
    // Arrange
    CategoryDto categoryDto = new CategoryDto(1L, "Medicamentos", "Medicamentos veterinarios");
    ProductDto productDto = new ProductDto(1L, "Medicine X", categoryDto, "Veterinary medicine", 49.99, 200);

    // Act
    Product result = ProductMapper.getInstance().fromProductDtoToProduct(productDto);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Medicine X", result.getName());
    assertEquals("Veterinary medicine", result.getDescription());
    assertEquals(49.99, result.getPrice());
    assertEquals(200, result.getStock());
    assertNotNull(result.getCategory());
  }

  @Test
  void testFromProductDtoToProduct_Null() {
    // Act
    Product result = ProductMapper.getInstance().fromProductDtoToProduct(null);

    // Assert
    assertNull(result);
  }

  @Test
  void testFromProductToProductDto_Success() {
    // Arrange
    Category category = new Category(1L, "Accesorios", "Accesorios para mascotas");
    Product product = new Product(1L, "Dog Collar", category, "Colorful dog collar", 15.99, 300);

    // Act
    ProductDto result = ProductMapper.getInstance().fromProductToProductDto(product);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.id());
    assertEquals("Dog Collar", result.name());
    assertEquals("Colorful dog collar", result.description());
    assertEquals(15.99, result.price());
    assertEquals(300, result.stock());
    assertNotNull(result.category());
  }

  @Test
  void testFromProductToProductDto_Null() {
    // Act
    ProductDto result = ProductMapper.getInstance().fromProductToProductDto(null);

    // Assert
    assertNull(result);
  }
}
