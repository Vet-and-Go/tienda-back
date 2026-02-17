package com.grupo4.VetAndGo.nanoServices.payment.impl;

import com.grupo4.VetAndGo.controller.dto.CardPaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(paymentService, "bancoApiUrl", "http://localhost:8080");
    }

    @Test
    void processPayment_ShouldCallRestTemplateWithCorrectPayload() {
        CardPaymentRequest request = new CardPaymentRequest(
                "userLogin", "apiToken", "1234567812345678", "12/25", "123", "John Doe",
                "ES1234567890123456789012", 100.0, "Payment Concept"
        );

        paymentService.processPayment(request);

        String expectedUrl = "http://localhost:8080/api/bank-accounts/payment";
        
        verify(restTemplate).postForEntity(eq(expectedUrl), any(Map.class), eq(Void.class));
    }
}
