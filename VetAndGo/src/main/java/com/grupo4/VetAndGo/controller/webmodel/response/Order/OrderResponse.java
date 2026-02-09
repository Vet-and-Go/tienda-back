package com.grupo4.VetAndGo.controller.webmodel.response.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.grupo4.VetAndGo.controller.webmodel.response.User.UserDetail;
import com.grupo4.VetAndGo.domain.model.enums.OrderState;

public record OrderResponse(
    Long id,
    List<OrderItemResponse> items,
    OrderState state,
    UserDetail user,
    LocalDateTime orderDate,
    BigDecimal totalAmount) {
}
