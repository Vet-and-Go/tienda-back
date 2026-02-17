package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.controller.dto.CardPaymentRequest;
import com.grupo4.VetAndGo.domain.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import com.grupo4.VetAndGo.spring.AuthFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthFilter.class))
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void processPayment_ShouldCallServiceWithFixedIban() throws Exception {
        CardPaymentRequest request = new CardPaymentRequest(
                "login",
                "token",
                "1234123412341234",
                "12/26",
                "123",
                "Full Name",
                "inputIban",
                100.0,
                "Concept"
        );

        mockMvc.perform(post("/api/payments/process")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Pago procesado exitosamente"));

        ArgumentCaptor<CardPaymentRequest> captor = ArgumentCaptor.forClass(CardPaymentRequest.class);
        verify(paymentService).processPayment(captor.capture());

        CardPaymentRequest capturedRequest = captor.getValue();
        assertEquals("ES61 1234 5678 9012 3456 7890", capturedRequest.iban());
        assertEquals("login", capturedRequest.login());
        assertEquals("Concept", capturedRequest.concept());
    }
}
