package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.dto.OrderItemDto;
import com.grupo4.VetAndGo.domain.model.OrderItem;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderItemJpaEntity;

public class OrderItemMapper {

  public static OrderItem fromOrderItemJpaEntityToOrderItem(OrderItemJpaEntity entity) {
    if (entity == null) {
      return null;
    }
    return new OrderItem(
        entity.getId(),
        ProductMapper.getInstance().fromProductJpaEntityToProduct(entity.getProduct()),
        entity.getQuantity(),
        entity.getUnitPrice(),
        entity.getSubtotal());
  }

  public static OrderItemJpaEntity fromOrderItemToOrderItemJpaEntity(OrderItem orderItem) {
    if (orderItem == null) {
      return null;
    }
    OrderItemJpaEntity entity = new OrderItemJpaEntity();
    entity.setId(orderItem.getId());
    entity.setProduct(ProductMapper.getInstance().fromProductToProductJpaEntity(orderItem.getProduct()));
    entity.setQuantity(orderItem.getQuantity());
    entity.setUnitPrice(orderItem.getUnitPrice());
    entity.setSubtotal(orderItem.getSubtotal());
    return entity;
  }

  public static OrderItemDto fromOrderItemToOrderItemDto(OrderItem orderItem) {
    if (orderItem == null) {
      return null;
    }
    return new OrderItemDto(
        orderItem.getId(),
        ProductMapper.getInstance().fromProductToProductDto(orderItem.getProduct()),
        orderItem.getQuantity(),
        orderItem.getUnitPrice(),
        orderItem.getSubtotal());
  }

  public static OrderItem fromOrderItemDtoToOrderItem(OrderItemDto dto) {
    if (dto == null) {
      return null;
    }
    return new OrderItem(
        dto.id(),
        ProductMapper.getInstance().fromProductDtoToProduct(dto.product()),
        dto.quantity(),
        dto.unitPrice(),
        dto.subtotal());
  }
}
