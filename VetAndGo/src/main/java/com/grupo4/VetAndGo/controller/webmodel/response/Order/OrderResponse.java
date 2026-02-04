package com.grupo4.VetAndGo.controller.webmodel.response.Order;

import java.util.List;
import java.util.Optional;

import com.grupo4.VetAndGo.controller.webmodel.response.ProductResponse;
import com.grupo4.VetAndGo.controller.webmodel.response.User.UserDetail;
import com.grupo4.VetAndGo.domain.model.enums.OrderState;

public record OrderResponse(
    Long id,
    List<ProductResponse> products,
    OrderState state,
    UserDetail user) {
}
