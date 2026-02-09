package com.grupo4.VetAndGo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.VetAndGo.controller.webmodel.request.Order.OrderInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.Order.OrderItemRequest;
import com.grupo4.VetAndGo.controller.webmodel.request.Order.OrderUpdate;
import com.grupo4.VetAndGo.controller.webmodel.response.Order.OrderResponse;
import com.grupo4.VetAndGo.domain.dto.OrderDto;
import com.grupo4.VetAndGo.domain.model.enums.OrderState;
import com.grupo4.VetAndGo.domain.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("")
  public ResponseEntity<List<OrderResponse>> getAllOrders(@RequestParam(required = false) Long userId) {
    List<OrderDto> orders = orderService.getAll(userId);
    List<OrderResponse> response = orders.stream()
        .map(com.grupo4.VetAndGo.controller.mapper.OrderMapper::fromOrderDtoToOrderResponse)
        .toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
    OrderDto order = orderService.getById(id);
    return ResponseEntity.ok(com.grupo4.VetAndGo.controller.mapper.OrderMapper.fromOrderDtoToOrderResponse(order));
  }

  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(
      @Valid @RequestBody OrderInsert orderInsert, 
      @RequestParam Long userId) {
    
    Map<Long, Integer> productQuantities = orderInsert.items().stream()
        .collect(Collectors.toMap(
            OrderItemRequest::productId,
            OrderItemRequest::quantity,
            Integer::sum));
    
    OrderDto createdOrder = orderService.create(
        productQuantities, 
        orderInsert.state(), 
        userId);
    
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(com.grupo4.VetAndGo.controller.mapper.OrderMapper.fromOrderDtoToOrderResponse(createdOrder));
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponse> updateOrder(
      @PathVariable Long id,
      @Valid @RequestBody OrderUpdate orderUpdate) {
    
    Map<Long, Integer> productQuantities = null;
    if (orderUpdate.items() != null && !orderUpdate.items().isEmpty()) {
      productQuantities = orderUpdate.items().stream()
          .collect(Collectors.toMap(
              OrderItemRequest::productId,
              OrderItemRequest::quantity,
              Integer::sum));
    }
    
    OrderDto updatedOrder = orderService.update(
        id, 
        productQuantities, 
        orderUpdate.state());
    
    return ResponseEntity.ok(com.grupo4.VetAndGo.controller.mapper.OrderMapper.fromOrderDtoToOrderResponse(updatedOrder));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/state/{state}")
  public ResponseEntity<OrderResponse> changeOrderState(
      @PathVariable Long id, 
      @PathVariable OrderState state) {
    orderService.changeState(id, state);
    OrderDto order = orderService.getById(id);
    return ResponseEntity.ok(com.grupo4.VetAndGo.controller.mapper.OrderMapper.fromOrderDtoToOrderResponse(order));
  }
}
