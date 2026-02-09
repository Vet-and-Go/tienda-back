package com.grupo4.VetAndGo.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.grupo4.VetAndGo.domain.model.enums.OrderState;

import jakarta.validation.constraints.NotNull;

public record OrderDto(
    Long id,
    List<OrderItemDto> items,
    @NotNull(message = "State must not be null") OrderState state,
    UserDto user,
    LocalDateTime orderDate,
    BigDecimal totalAmount) {

  public OrderDto(
      Long id,
      List<OrderItemDto> items,
      OrderState state,
      UserDto user,
      LocalDateTime orderDate,
      BigDecimal totalAmount) {
    this.id = id;
    this.items = items;
    this.state = state;
    this.user = user;
    this.orderDate = orderDate;
    this.totalAmount = totalAmount;
  }
}
