package com.grupo4.VetAndGo.controller.webmodel.response;

import com.grupo4.VetAndGo.controller.webmodel.response.Order.OrderResponse;

public record CheckoutResponse(
    OrderResponse order,
    String paymentStatus,
    String message
) {}
