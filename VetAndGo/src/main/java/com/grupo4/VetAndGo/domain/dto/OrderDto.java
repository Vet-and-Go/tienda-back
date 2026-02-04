package com.grupo4.VetAndGo.domain.dto;

import java.util.List;

import com.grupo4.VetAndGo.domain.model.enums.OrderState;

import jakarta.validation.constraints.NotNull;

public record OrderDto(
    Long id,
    List<ProductDto> products,
    @NotNull(message = "State must not be null") OrderState state,
    UserDto user) {

  public OrderDto(
      Long id,
      List<ProductDto> products,
      OrderState state,
      UserDto user) {
    this.id = id;
    this.products = products;
    this.state = state;
    this.user = user;
  }
}
