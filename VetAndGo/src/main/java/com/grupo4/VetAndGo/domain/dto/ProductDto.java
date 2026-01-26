package com.grupo4.VetAndGo.domain.dto;


import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductDto(
    Long id,
    String name,
    CategoryDto category,
    String description,
    @NotNull(message = "El precio base no puede ser nulo") BigDecimal basePrice,
    Integer stock,
    BigDecimal discountPercentage,
    BigDecimal finalPrice,
    String imageUrl) {

  public ProductDto(
      Long id,
      String name,
      CategoryDto category,
      String description,
      BigDecimal basePrice,
      Integer stock,
      BigDecimal discountPercentage,
      BigDecimal finalPrice,
      String imageUrl) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.stock = stock;
    this.basePrice = basePrice;
    this.category = category;
    this.discountPercentage = discountPercentage;
    this.finalPrice = finalPrice != null ? finalPrice : basePrice;
    this.imageUrl = imageUrl;
  }

  // Constructor de conveniencia para compatibilidad con tests (sin descuento)
  public ProductDto(
      Long id,
      String name,
      CategoryDto category,
      String description,
      BigDecimal basePrice,
      Integer stock,
      String imageUrl) {
    this(id, name, category, description, basePrice, stock, BigDecimal.ZERO, basePrice, imageUrl);
  }

  // Constructor de conveniencia para compatibilidad con tests (precio como double)
  public ProductDto(
      Long id,
      String name,
      CategoryDto category,
      String description,
      double basePrice,
      Integer stock) {
    this(id, name, category, description, BigDecimal.valueOf(basePrice), stock, BigDecimal.ZERO, BigDecimal.valueOf(basePrice), null);
  }
}
