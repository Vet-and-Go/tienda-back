package com.grupo4.VetAndGo.controller;

import com.grupo4.VetAndGo.controller.dto.CardPaymentRequest;
import com.grupo4.VetAndGo.domain.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<Void> processPayment(@RequestBody CardPaymentRequest request) {
        paymentService.processPayment(request);
        return ResponseEntity.ok().build();
    }
}
