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
    public ResponseEntity<String> processPayment(@RequestBody CardPaymentRequest request) {
        CardPaymentRequest storeRequest = new CardPaymentRequest(
            request.login(),
            request.apiToken(),
            request.cardNumber(),
            request.expirationDate(),
            request.cvc(),
            request.fullName(),
            "ES61 1234 5678 9012 3456 7890",
            request.amount(),
            request.concept()
        );
        paymentService.processPayment(storeRequest);
        return ResponseEntity.ok("Pago procesado exitosamente");
    }
}
