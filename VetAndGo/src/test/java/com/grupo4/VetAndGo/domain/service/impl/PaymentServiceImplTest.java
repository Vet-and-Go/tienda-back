package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.controller.dto.CardPaymentRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private com.grupo4.VetAndGo.nanoServices.payment.PaymentService nanoPaymentService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void processPayment_ShouldCallNanoPaymentService() {
        CardPaymentRequest request = new CardPaymentRequest("login", "token", "1234", "12/24", "123", "Name", "IBAN", 100.0, "Concept");

        paymentService.processPayment(request);

        verify(nanoPaymentService, times(1)).processPayment(request);
    }
}
