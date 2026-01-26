package com.grupo4.VetAndGo.controller.webmodel.response;


import com.grupo4.VetAndGo.controller.webmodel.response.CategoryResponse;
import java.math.BigDecimal;

public record ProductResponse(
    Long id,
    String name,
    CategoryResponse category,
    String description,
    BigDecimal basePrice,
    Integer stock,
    BigDecimal discountPercentage,
    BigDecimal finalPrice,
    String imageUrl) {
}
