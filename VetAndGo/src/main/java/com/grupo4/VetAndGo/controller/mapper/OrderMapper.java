package com.grupo4.VetAndGo.controller.mapper;

import java.util.Collections;
import java.util.stream.Collectors;

import com.grupo4.VetAndGo.controller.webmodel.response.Order.OrderItemResponse;
import com.grupo4.VetAndGo.controller.webmodel.response.Order.OrderResponse;
import com.grupo4.VetAndGo.domain.dto.OrderDto;
import com.grupo4.VetAndGo.domain.dto.OrderItemDto;

public class OrderMapper {

  public static OrderResponse fromOrderDtoToOrderResponse(OrderDto orderDto) {
    if (orderDto == null) {
      return null;
    }
    return new OrderResponse(
        orderDto.id(),
        orderDto.items() != null
            ? orderDto.items().stream()
                .map(OrderMapper::fromOrderItemDtoToOrderItemResponse)
                .collect(Collectors.toList())
            : Collections.emptyList(),
        orderDto.state(),
        UserMapper.fromUserDtoToUserDetail(orderDto.user()),
        orderDto.orderDate(),
        orderDto.totalAmount());
  }

  public static OrderItemResponse fromOrderItemDtoToOrderItemResponse(OrderItemDto itemDto) {
    if (itemDto == null) {
      return null;
    }
    return new OrderItemResponse(
        itemDto.id(),
        ProductMapper.fromProductDtoToProductResponse(itemDto.product()),
        itemDto.quantity(),
        itemDto.unitPrice(),
        itemDto.subtotal());
  }

  public static OrderDto fromOrderResponseToOrderDto(OrderResponse orderResponse) {
    if (orderResponse == null) {
      return null;
    }
    return new OrderDto(
        orderResponse.id(),
        orderResponse.items() != null
            ? orderResponse.items().stream()
                .map(OrderMapper::fromOrderItemResponseToOrderItemDto)
                .collect(Collectors.toList())
            : Collections.emptyList(),
        orderResponse.state(),
        UserMapper.fromUserDetailToUserDto(orderResponse.user()),
        orderResponse.orderDate(),
        orderResponse.totalAmount());
  }

  public static OrderItemDto fromOrderItemResponseToOrderItemDto(OrderItemResponse itemResponse) {
    if (itemResponse == null) {
      return null;
    }
    return new OrderItemDto(
        itemResponse.id(),
        ProductMapper.fromProductResponseToProductDto(itemResponse.product()),
        itemResponse.quantity(),
        itemResponse.unitPrice(),
        itemResponse.subtotal());
  }

  public static OrderDto fromOrderInsertToOrderDto(OrderResponse orderInsert) {
    if (orderInsert == null) {
      return null;
    }
    return new OrderDto(
        null,
        orderInsert.items() != null
            ? orderInsert.items().stream()
                .map(OrderMapper::fromOrderItemResponseToOrderItemDto)
                .collect(Collectors.toList())
            : Collections.emptyList(),
        orderInsert.state(),
        UserMapper.fromUserDetailToUserDto(orderInsert.user()),
        orderInsert.orderDate(),
        orderInsert.totalAmount());
  }

  public static OrderDto fromOrderUpdateToOrderDto(OrderResponse orderUpdate) {
    if (orderUpdate == null) {
      return null;
    }
    return new OrderDto(
        orderUpdate.id(),
        orderUpdate.items() != null
            ? orderUpdate.items().stream()
                .map(OrderMapper::fromOrderItemResponseToOrderItemDto)
                .collect(Collectors.toList())
            : Collections.emptyList(),
        orderUpdate.state(),
        UserMapper.fromUserDetailToUserDto(orderUpdate.user()),
        orderUpdate.orderDate(),
        orderUpdate.totalAmount());
  }

}
