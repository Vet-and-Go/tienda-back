package com.grupo4.VetAndGo.controller.webmodel.request.Order;

import java.util.List;

import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.domain.model.enums.OrderState;

public record OrderInsert(
    Long id,
    List<Product> products,
    OrderState state,
    User user) {

}
