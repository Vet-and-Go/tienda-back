package com.grupo4.VetAndGo.domain.service.impl;

import java.util.List;

import com.grupo4.VetAndGo.controller.webmodel.request.Order.OrderInsert;
import com.grupo4.VetAndGo.domain.dto.OrderDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.exception.ResourceNotFoundException;
import com.grupo4.VetAndGo.domain.mapper.OrderMapper;
import com.grupo4.VetAndGo.domain.mapper.ProductMapper;
import com.grupo4.VetAndGo.domain.repository.OrderRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.domain.service.OrderService;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;

public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;
  private ProductRepository productRepository;
  private UserRepository userRepository;

  public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<OrderDto> getAll(Long userId) {
    List<OrderJpaEntity> orders = orderRepository.getAll(userId);

    if (orders.isEmpty()) {
      throw new ResourceNotFoundException("No orders found for the user.");
    }

    return orders.stream()
        .map(OrderMapper::fromOrderJpaEntityToOrder)
        .map(OrderMapper::fromOrderToOrderDto)
        .toList();
  }

  @Override
  public OrderDto getById(Long id) {
    return orderRepository.getById(id)
        .map(OrderMapper::fromOrderJpaEntityToOrder)
        .map(OrderMapper::fromOrderToOrderDto)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

  }

  @Override
  public OrderDto create(OrderInsert order, UserDto user) {
    if (orderRepository.getById(order.id()).isPresent()) {
      throw new IllegalArgumentException("Order with the same ID already exists.");
    }

    ProductDto productDto = productRepository.findById(order.id())
        .map(ProductMapper.getInstance()::fromProductToProductDto)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    OrderDto orderDto = new OrderDto(
        order.id(),
        List.of(productDto),
        order.state(),
        user);

    return OrderMapper.fromOrderToOrderDto(
        OrderMapper.fromOrderJpaEntityToOrder(
            orderRepository.save(
                OrderMapper.fromOrderToOrderJpaEntity(
                    OrderMapper.fromOrderDtoToOrder(orderDto)))));
  }

  @Override
  public OrderDto update(OrderDto orderDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(Long id) {
    getById(id);
    orderRepository.delete(id);
  }

  @Override
  public List<ProductDto> getProductsFromOrder(Long orderId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProductsFromOrder'");
  }

  @Override
  public void orderToPending(Long id) {
    // Bring the Order with the Id, then change the state. º↓º
  }

  @Override
  public void orderToProcessed(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'orderToProcessed'");
  }

}
