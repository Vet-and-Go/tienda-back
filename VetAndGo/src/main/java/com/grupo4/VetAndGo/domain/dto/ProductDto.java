package com.grupo4.VetAndGo.domain.dto;


import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import jakarta.validation.constraints.*;

public record ProductDto(
    Long id,
    String name,
    CategoryDto category,
    String description,
    @NotNull(message = "El precio base no puede ser nulo") Double price,
    Integer stock) {

  public ProductDto(
      Long id,
      String name,
      CategoryDto category,
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
