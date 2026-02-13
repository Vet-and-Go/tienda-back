package com.grupo4.VetAndGo.nanoServices.payment.impl;

import com.grupo4.VetAndGo.controller.dto.CardPaymentRequest;
import com.grupo4.VetAndGo.nanoServices.payment.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;

    @Value("${banco.api.url}")
    private String bancoApiUrl;

    public PaymentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void processPayment(CardPaymentRequest request) {
        Map<String, Object> payload = Map.of(
            "authorization", Map.of(
                "login", request.login(),
                "api_token", request.apiToken()
            ),
            "origin", Map.of(
                "cardNumber", request.cardNumber(),
                "expirationDate", request.expirationDate(),
                "cvc", request.cvc(),
                "fullName", request.fullName()
            ),
            "destination", Map.of(
                "iban", request.iban()
            ),
            "paymentDetails", Map.of(
                "amount", request.amount(),
                "concept", request.concept()
            )
        );

        restTemplate.postForEntity(bancoApiUrl + "/api/bank-accounts/payment", payload, Void.class);
    }
}
