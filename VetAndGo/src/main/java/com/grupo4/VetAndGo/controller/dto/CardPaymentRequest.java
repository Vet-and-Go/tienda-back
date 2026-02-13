package com.grupo4.VetAndGo.controller.dto;

public record CardPaymentRequest(
    String login,
    String apiToken,
    String cardNumber,
    String expirationDate,
    String cvc,
    String fullName,
    String iban,
    Double amount,
    String concept
) {}
