package com.grupo4.VetAndGo.controller.webmodel.request;

import java.util.List;

import com.grupo4.VetAndGo.controller.webmodel.request.Order.OrderItemRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CheckoutRequest(
    @NotEmpty(message = "Order must have at least one item")
    @Valid
    List<OrderItemRequest> items,
    
    @NotNull(message = "Card number must not be null")
    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    String cardNumber,
    
    @NotNull(message = "Expiration date must not be null")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Expiration date must be in format MM/YY")
    String expirationDate,
    
    @NotNull(message = "CVC must not be null")
    @Pattern(regexp = "\\d{3,4}", message = "CVC must be 3 or 4 digits")
    String cvc,
    
    @NotNull(message = "Full name must not be null")
    String fullName,
    
    @NotNull(message = "Login must not be null")
    String login,
    
    @NotNull(message = "API token must not be null")
    String apiToken,
    
    @NotNull(message = "Concept must not be null")
    String concept
) {}
