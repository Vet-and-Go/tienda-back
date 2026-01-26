package com.grupo4.VetAndGo.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Model Tests")
class ProductTest {

  @Test
  @DisplayName("Should calculate final price without discount")
  void testCalculateFinalPriceWithoutDiscount() {
    // Arrange
    Category category = new Category(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("100.00");

    // Act
    Product product = new Product(
        1L,
        "Dog Food",
        category,
        "Premium dog food",
        basePrice,
        50,
        BigDecimal.ZERO,
        null,
        "http://image.url"
    );

    // Assert
    assertNotNull(product);
    assertEquals(basePrice.setScale(2, RoundingMode.HALF_UP), product.getFinalPrice());
    assertEquals(basePrice, product.getBasePrice());
  }

  @Test
  @DisplayName("Should calculate final price with 10% discount")
  void testCalculateFinalPriceWith10PercentDiscount() {
    // Arrange
    Category category = new Category(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("100.00");
    BigDecimal discountPercentage = new BigDecimal("10.00");
    BigDecimal expectedFinalPrice = new BigDecimal("90.00");

    // Act
    Product product = new Product(
        1L,
        "Dog Food",
        category,
        "Premium dog food",
        basePrice,
        50,
        discountPercentage,
        null, // Let it calculate
        "http://image.url"
    );

    // Assert
    assertNotNull(product);
    assertEquals(expectedFinalPrice, product.getFinalPrice());
    assertEquals(basePrice, product.getBasePrice());
    assertEquals(discountPercentage, product.getDiscountPercentage());
  }

  @Test
  @DisplayName("Should calculate final price with 20% discount")
  void testCalculateFinalPriceWith20PercentDiscount() {
    // Arrange
    Category category = new Category(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("50.00");
    BigDecimal discountPercentage = new BigDecimal("20.00");
    BigDecimal expectedFinalPrice = new BigDecimal("40.00");

    // Act
    Product product = new Product(
        1L,
        "Cat Food",
        category,
        "Premium cat food",
        basePrice,
        30,
        discountPercentage,
        null,
        "http://image.url"
    );

    // Assert
    assertEquals(expectedFinalPrice, product.getFinalPrice());
    assertEquals(basePrice, product.getBasePrice());
  }

  @Test
  @DisplayName("Should calculate final price with 50% discount")
  void testCalculateFinalPriceWith50PercentDiscount() {
    // Arrange
    Category category = new Category(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("80.00");
    BigDecimal discountPercentage = new BigDecimal("50.00");
    BigDecimal expectedFinalPrice = new BigDecimal("40.00");

    // Act
    Product product = new Product(
        1L,
        "Bird Food",
        category,
        "Premium bird food",
        basePrice,
        100,
        discountPercentage,
        null,
        "http://image.url"
    );

    // Assert
    assertEquals(expectedFinalPrice, product.getFinalPrice());
  }

  @Test
  @DisplayName("Should recalculate final price when base price changes")
  void testRecalculateFinalPriceWhenBasePriceChanges() {
    // Arrange
    Category category = new Category(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("100.00");
    BigDecimal discountPercentage = new BigDecimal("10.00");

    Product product = new Product(
        1L,
        "Dog Food",
        category,
        "Premium dog food",
        basePrice,
        50,
        discountPercentage,
        null,
        "http://image.url"
    );

    // Act
    BigDecimal newBasePrice = new BigDecimal("200.00");
    product.setBasePrice(newBasePrice);
    BigDecimal expectedNewFinalPrice = new BigDecimal("180.00");

    // Assert
    assertEquals(expectedNewFinalPrice, product.getFinalPrice());
  }

  @Test
  @DisplayName("Should recalculate final price when discount changes")
  void testRecalculateFinalPriceWhenDiscountChanges() {
    // Arrange
    Category category = new Category(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("100.00");
    BigDecimal discountPercentage = new BigDecimal("10.00");

    Product product = new Product(
        1L,
        "Dog Food",
        category,
        "Premium dog food",
        basePrice,
        50,
        discountPercentage,
        null,
        "http://image.url"
    );

    // Act
    BigDecimal newDiscountPercentage = new BigDecimal("25.00");
    product.setDiscountPercentage(newDiscountPercentage);
    BigDecimal expectedNewFinalPrice = new BigDecimal("75.00");

    // Assert
    assertEquals(expectedNewFinalPrice, product.getFinalPrice());
  }

  @Test
  @DisplayName("Should handle null discount as zero discount")
  void testNullDiscountAsZero() {
    // Arrange
    Category category = new Category(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("50.00");

    // Act
    Product product = new Product(
        1L,
        "Fish Food",
        category,
        "Premium fish food",
        basePrice,
        20,
        null, // Null discount
        null,
        "http://image.url"
    );

    // Assert
    assertEquals(basePrice.setScale(2, RoundingMode.HALF_UP), product.getFinalPrice());
  }

  @Test
  @DisplayName("Should use provided final price if not null")
  void testUseProvidedFinalPrice() {
    // Arrange
    Category category = new Category(1L, "Alimentos", "Comida para mascotas");
    BigDecimal basePrice = new BigDecimal("100.00");
    BigDecimal discountPercentage = new BigDecimal("10.00");
    BigDecimal providedFinalPrice = new BigDecimal("85.00"); // Custom price

    // Act
    Product product = new Product(
        1L,
        "Special Product",
        category,
        "Special pricing",
        basePrice,
        50,
        discountPercentage,
        providedFinalPrice, // Explicitly provided
        "http://image.url"
    );

    // Assert
    assertEquals(providedFinalPrice, product.getFinalPrice());
  }
}
