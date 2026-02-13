package com.grupo4.VetAndGo.domain.service;

import java.util.List;
import java.util.Map;

import com.grupo4.VetAndGo.domain.dto.OrderDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.model.enums.OrderState;

public interface OrderService {
  List<OrderDto> getAll(Long userId);

  OrderDto getById(Long id);

  List<ProductDto> getProductsFromOrder(Long orderId);

  OrderDto create(Map<Long, Integer> productQuantities, OrderState state, Long userId);

  OrderDto update(Long id, Map<Long, Integer> productQuantities, OrderState state);

  void changeState(Long id, OrderState newState);

  void delete(Long id);

  OrderDto checkout(Map<Long, Integer> productQuantities, Long userId, String cardNumber, 
      String expirationDate, String cvc, String fullName, String login, String apiToken, String concept);
}
