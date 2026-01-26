package com.grupo4.VetAndGo.controller.webmodel.request.product;

import java.math.BigDecimal;

public record ProductInsert(
    String name,
    Long category,
    String description,
    BigDecimal basePrice,
    Integer stock,
    BigDecimal discountPercentage,
    String imageUrl) {
}
