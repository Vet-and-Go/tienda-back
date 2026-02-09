package com.grupo4.VetAndGo.domain.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.grupo4.VetAndGo.domain.dto.OrderDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.exception.ResourceNotFoundException;
import com.grupo4.VetAndGo.domain.mapper.OrderMapper;
import com.grupo4.VetAndGo.domain.mapper.ProductMapper;
import com.grupo4.VetAndGo.domain.model.Order;
import com.grupo4.VetAndGo.domain.model.OrderItem;
import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.domain.model.enums.OrderState;
import com.grupo4.VetAndGo.domain.repository.OrderRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.domain.service.OrderService;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderItemJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;
  private ProductRepository productRepository;
  private UserRepository userRepository;

  public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
      UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
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
  public OrderDto create(Map<Long, Integer> productQuantities, OrderState state, Long userId) {
    UserJpaEntity user = userRepository.findById(userId);
    if (user == null) {
      throw new ResourceNotFoundException("User not found");
    }

    OrderJpaEntity orderJpaEntity = new OrderJpaEntity();
    orderJpaEntity.setState(state != null ? state : OrderState.PENDING);
    orderJpaEntity.setUser(user);
    orderJpaEntity.setOrderDate(LocalDateTime.now());

    List<OrderItemJpaEntity> items = createOrderItems(orderJpaEntity, productQuantities);
    
    BigDecimal totalAmount = items.stream()
        .map(OrderItemJpaEntity::getSubtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    orderJpaEntity.setItems(items);
    orderJpaEntity.setTotalAmount(totalAmount);

    return OrderMapper.fromOrderToOrderDto(
        OrderMapper.fromOrderJpaEntityToOrder(
            orderRepository.save(orderJpaEntity)));
  }

  @Override
  public OrderDto update(Long id, Map<Long, Integer> productQuantities, OrderState state) {
    return orderRepository.getById(id)
        .map(existing -> {
          if (state != null) {
            existing.setState(state);
          }
          
          if (productQuantities != null && !productQuantities.isEmpty()) {
            List<OrderItemJpaEntity> items = createOrderItems(existing, productQuantities);
            
            BigDecimal totalAmount = items.stream()
                .map(OrderItemJpaEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            existing.getItems().clear();
            existing.getItems().addAll(items);
            existing.setTotalAmount(totalAmount);
          }
          
          return orderRepository.save(existing);
        })
        .map(OrderMapper::fromOrderJpaEntityToOrder)
        .map(OrderMapper::fromOrderToOrderDto)
        .orElseThrow(() -> new ResourceNotFoundException("Order with ID " + id + " not found."));
  }

  @Override
  public void delete(Long id) {
    getById(id);
    orderRepository.delete(id);
  }

  @Override
  public List<ProductDto> getProductsFromOrder(Long orderId) {
    return orderRepository.getById(orderId)
        .map(OrderJpaEntity::getItems)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found"))
        .stream()
        .map(OrderItemJpaEntity::getProduct)
        .distinct()
        .map(ProductMapper.getInstance()::fromProductJpaEntityToProduct)
        .map(ProductMapper.getInstance()::fromProductToProductDto)
        .toList();
  }

  @Override
  public void changeState(Long id, OrderState newState) {
    orderRepository.getById(id)
        .map(existing -> {
          existing.setState(newState);
          return orderRepository.save(existing);
        })
        .orElseThrow(() -> new ResourceNotFoundException("Order with ID " + id + " not found."));
  }

  private List<OrderItemJpaEntity> createOrderItems(OrderJpaEntity order, Map<Long, Integer> productQuantities) {
    List<OrderItemJpaEntity> items = new ArrayList<>();

    for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
      Long productId = entry.getKey();
      Integer quantity = entry.getValue();

      Product product = productRepository.findById(productId)
          .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found"));

      OrderItem orderItem = OrderItem.create(product, quantity);
      
      ProductJpaEntity productEntity = ProductMapper.getInstance().fromProductToProductJpaEntity(product);

      OrderItemJpaEntity itemEntity = new OrderItemJpaEntity();
      itemEntity.setOrder(order);
      itemEntity.setProduct(productEntity);
      itemEntity.setQuantity(orderItem.getQuantity());
      itemEntity.setUnitPrice(orderItem.getUnitPrice());
      itemEntity.setSubtotal(orderItem.getSubtotal());

      items.add(itemEntity);
    }

    return items;
  }
}
