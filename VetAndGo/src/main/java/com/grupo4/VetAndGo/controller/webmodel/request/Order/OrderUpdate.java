package com.grupo4.VetAndGo.controller.webmodel.request.Order;

import java.util.List;

import com.grupo4.VetAndGo.domain.model.enums.OrderState;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderUpdate(
    @NotNull(message = "Order ID must not be null")
    Long id,
    
    @Valid
    List<OrderItemRequest> items,
    
    OrderState state) {
}
