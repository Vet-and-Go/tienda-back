package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.dto.OrderDto;
import com.grupo4.VetAndGo.domain.model.Order;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;

public class OrderMapper {

  public static Order fromOrderJpaEntityToOrder(OrderJpaEntity orderJpaEntity) {
    if (orderJpaEntity == null) {
      return null;
    }
    return new Order(
        orderJpaEntity.getId(),
        orderJpaEntity.getItems().stream()
            .map(OrderItemMapper::fromOrderItemJpaEntityToOrderItem)
            .toList(),
        orderJpaEntity.getState(),
        UserMapper.fromUserJpaEntityToUser(orderJpaEntity.getUser()),
        orderJpaEntity.getOrderDate(),
        orderJpaEntity.getTotalAmount());
  }

  public static OrderJpaEntity fromOrderToOrderJpaEntity(Order order) {
    if (order == null) {
      return null;
    }
    OrderJpaEntity orderJpaEntity = new OrderJpaEntity();
    orderJpaEntity.setId(order.getId());
    orderJpaEntity.setItems(order.getItems().stream()
        .map(OrderItemMapper::fromOrderItemToOrderItemJpaEntity)
        .toList());
    orderJpaEntity.setState(order.getState());
    orderJpaEntity.setUser(UserMapper.fromUserToUserJpaEntity(order.getUser()));
    orderJpaEntity.setOrderDate(order.getOrderDate());
    orderJpaEntity.setTotalAmount(order.getTotalAmount());
    return orderJpaEntity;
  }

  public static OrderDto fromOrderToOrderDto(Order order) {
    if (order == null) {
      return null;
    }
    return new OrderDto(
        order.getId(),
        order.getItems().stream()
            .map(OrderItemMapper::fromOrderItemToOrderItemDto)
            .toList(),
        order.getState(),
        UserMapper.fromUserToUserDto(order.getUser()),
        order.getOrderDate(),
        order.getTotalAmount());
  }

  public static Order fromOrderDtoToOrder(OrderDto orderDto) {
    if (orderDto == null) {
      return null;
    }
    return new Order(
        orderDto.id(),
        orderDto.items().stream()
            .map(OrderItemMapper::fromOrderItemDtoToOrderItem)
            .toList(),
        orderDto.state(),
        UserMapper.fromUserDtoToUser(orderDto.user()),
        orderDto.orderDate(),
        orderDto.totalAmount());
  }
}
