package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.controller.dto.CardPaymentRequest;

public interface PaymentService {
    
    void processPayment(CardPaymentRequest request);
}
