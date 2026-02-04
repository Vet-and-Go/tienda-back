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
        orderJpaEntity.getProducts().stream()
            .map(ProductMapper.getInstance()::fromProductJpaEntityToProduct)
            .toList(),
        orderJpaEntity.getState(),
        UserMapper.fromUserJpaEntityToUser(orderJpaEntity.getUser()));
  }

  public static OrderJpaEntity fromOrderToOrderJpaEntity(Order order) {
    if (order == null) {
      return null;
    }
    OrderJpaEntity orderJpaEntity = new OrderJpaEntity();
    orderJpaEntity.setId(order.getId());
    orderJpaEntity.setProducts(order.getProducts().stream()
        .map(ProductMapper.getInstance()::fromProductToProductJpaEntity)
        .toList());
    orderJpaEntity.setState(order.getState());
    orderJpaEntity.setUser(UserMapper.fromUserToUserJpaEntity(order.getUser()));
    return orderJpaEntity;
  }

  public static OrderDto fromOrderToOrderDto(Order order) {
    if (order == null) {
      return null;
    }
    return new OrderDto(
        order.getId(),
        order.getProducts().stream()
            .map(ProductMapper.getInstance()::fromProductToProductDto)
            .toList(),
        order.getState(),
        UserMapper.fromUserToUserDto(order.getUser()));
  }

  public static Order fromOrderDtoToOrder(OrderDto orderDto) {
    if (orderDto == null) {
      return null;
    }
    return new Order(
        orderDto.id(),
        orderDto.products().stream()
            .map(ProductMapper.getInstance()::fromProductDtoToProduct)
            .toList(),
        orderDto.state(),
        UserMapper.fromUserDtoToUser(orderDto.user()));
  }
}
