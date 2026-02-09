package com.grupo4.VetAndGo.domain.dto;

import java.math.BigDecimal;

public record OrderItemDto(
    Long id,
    ProductDto product,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal) {
}
