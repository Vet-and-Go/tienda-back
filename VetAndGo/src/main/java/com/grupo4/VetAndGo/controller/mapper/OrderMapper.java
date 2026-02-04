package com.grupo4.VetAndGo.controller.mapper;

import java.util.Collections;
import java.util.stream.Collectors;

import com.grupo4.VetAndGo.controller.webmodel.response.Order.OrderResponse;
import com.grupo4.VetAndGo.domain.dto.OrderDto;

public class OrderMapper {

  public static OrderResponse fromOrderDtoToOrderResponse(OrderDto orderDto) {
    if (orderDto == null) {
      return null;
    }
    return new OrderResponse(
        orderDto.id(),

        orderDto.products().stream()
            .map(ProductMapper::fromProductDtoToProductResponse)
            .collect(Collectors.toList()),

        orderDto.state(),
        UserMapper.fromUserDtoToUserDetail(orderDto.user()));
  }
}
