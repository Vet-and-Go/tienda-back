package com.grupo4.VetAndGo.controller.webmodel.request.Order;

import java.util.List;

import com.grupo4.VetAndGo.domain.model.enums.OrderState;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderInsert(
    @NotEmpty(message = "Order must have at least one item") 
    @Valid
    List<OrderItemRequest> items,
    
    @NotNull(message = "State must not be null")
    OrderState state) {
}
