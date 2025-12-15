package com.grupo4.VetAndGo.domain.dto;

import jakarta.validation.constraints.*;

public record ProductDto(
    Long id,
    String name,
    Long category,
    String description,
    @NotNull(message = "El precio base no puede ser nulo") Double price,
    Integer stock) {

  public ProductDto(
      Long id,
      String name,
      Long category,
      String description,
      Double price,
      Integer stock) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.stock = stock;
    this.price = price;
    this.category = category;
  }
}
