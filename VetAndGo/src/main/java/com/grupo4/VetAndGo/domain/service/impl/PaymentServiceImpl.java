package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.controller.dto.CardPaymentRequest;
import com.grupo4.VetAndGo.domain.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {

    private final com.grupo4.VetAndGo.nanoServices.payment.PaymentService nanoPaymentService;

    public PaymentServiceImpl(com.grupo4.VetAndGo.nanoServices.payment.PaymentService nanoPaymentService) {
        this.nanoPaymentService = nanoPaymentService;
    }

    @Override
    public void processPayment(CardPaymentRequest request) {
        nanoPaymentService.processPayment(request);
    }
}