package com.grupo4.VetAndGo.controller.webmodel.response.Order;

import java.math.BigDecimal;

import com.grupo4.VetAndGo.controller.webmodel.response.ProductResponse;

public record OrderItemResponse(
    Long id,
    ProductResponse product,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal) {
}
