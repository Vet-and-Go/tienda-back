package com.grupo4.VetAndGo.domain.service;

import java.util.List;

import com.grupo4.VetAndGo.controller.webmodel.request.Order.OrderInsert;
import com.grupo4.VetAndGo.domain.dto.OrderDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;

public interface OrderService {
  List<OrderDto> getAll(Long userId);

  OrderDto getById(Long id);

  List<ProductDto> getProductsFromOrder(Long orderId);

  // Bound to be OrderInsert and OrderUpdate º↓º
  OrderDto create(OrderInsert order, UserDto user);

  OrderDto update(OrderDto orderDto);

  void orderToPending(Long id);

  void orderToProcessed(Long id);

  void delete(Long id);
}
