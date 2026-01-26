package com.grupo4.VetAndGo.domain.dto;

 import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductDto Tests")
class ProductDtoTest {

  @Test
  @DisplayName("Should create ProductDto with basePrice and finalPrice")
  void testProductDtoCreation() {
    // Arrange
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("100.00");
    BigDecimal discountPercentage = BigDecimal.ZERO;
    BigDecimal finalPrice = new BigDecimal("100.00");

    // Act
    ProductDto productDto = new ProductDto(
        1L,
        "Dog Food",
        categoryDto,
        "Premium dog food",
        basePrice,
        50,
        discountPercentage,
        finalPrice,
        "http://image.url"
    );

    // Assert
    assertNotNull(productDto);
    assertEquals(1L, productDto.id());
    assertEquals("Dog Food", productDto.name());
    assertEquals(basePrice, productDto.basePrice());
    assertEquals(finalPrice, productDto.finalPrice());
    assertEquals(discountPercentage, productDto.discountPercentage());
  }

  @Test
  @DisplayName("Should create ProductDto with discount")
  void testProductDtoWithDiscount() {
    // Arrange
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("100.00");
    BigDecimal discountPercentage = new BigDecimal("20.00");
    BigDecimal expectedFinalPrice = new BigDecimal("80.00");

    // Act
    ProductDto productDto = new ProductDto(
        1L,
        "Dog Food",
        categoryDto,
        "Premium dog food",
        basePrice,
        50,
        discountPercentage,
        expectedFinalPrice,
        "http://image.url"
    );

    // Assert
    assertNotNull(productDto);
    assertEquals(basePrice, productDto.basePrice());
    assertEquals(expectedFinalPrice, productDto.finalPrice());
    assertEquals(discountPercentage, productDto.discountPercentage());
  }

  @Test
  @DisplayName("Should use convenience constructor without discount")
  void testConvenienceConstructorWithoutDiscount() {
    // Arrange
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("50.00");

    // Act
    ProductDto productDto = new ProductDto(
        1L,
        "Cat Food",
        categoryDto,
        "Premium cat food",
        basePrice,
        30,
        "http://image.url"
    );

    // Assert
    assertNotNull(productDto);
    assertEquals(basePrice, productDto.basePrice());
    assertEquals(basePrice, productDto.finalPrice()); // finalPrice should equal basePrice
    assertEquals(BigDecimal.ZERO, productDto.discountPercentage()); // discount should be 0
  }

  @Test
  @DisplayName("Should use convenience constructor with double price")
  void testConvenienceConstructorWithDouble() {
    // Arrange
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");

    // Act
    ProductDto productDto = new ProductDto(
        1L,
        "Bird Food",
        categoryDto,
        "Premium bird food",
        29.99,
        100
    );

    // Assert
    assertNotNull(productDto);
    assertEquals(new BigDecimal("29.99"), productDto.basePrice());
    assertEquals(new BigDecimal("29.99"), productDto.finalPrice());
    assertEquals(BigDecimal.ZERO, productDto.discountPercentage());
  }

  @Test
  @DisplayName("Should handle null finalPrice by using basePrice")
  void testNullFinalPrice() {
    // Arrange
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("75.00");

    // Act
    ProductDto productDto = new ProductDto(
        1L,
        "Fish Food",
        categoryDto,
        "Premium fish food",
        basePrice,
        20,
        BigDecimal.ZERO,
        null, // null finalPrice
        "http://image.url"
    );

    // Assert
    assertNotNull(productDto);
    assertEquals(basePrice, productDto.basePrice());
    assertEquals(basePrice, productDto.finalPrice()); // Should default to basePrice
  }
}
