//package com.grupo4.VetAndGo.nanoServices;
//
//import com.grupo4.VetAndGo.domain.model.BankAccount;
//import com.grupo4.VetAndGo.domain.model.CreditCard;
//import com.grupo4.VetAndGo.domain.service.BankTransactionService;
//import com.grupo4.VetAndGo.nanoServices.payment.impl.CardPaymentServiceImpl;
//import com.grupo4.VetAndGo.persistence.repository.BankAccountRepository;
//import com.grupo4.VetAndGo.persistence.repository.CreditCardRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class PaymentServiceImplTest {
//
//    @Mock
//    private CreditCardRepository creditCardRepository;
//
//    @Mock
//    private BankAccountRepository bankAccountRepository;
//
//    @Mock
//    private BankTransactionService bankTransactionService;
//
//    @InjectMocks
//    private CardPaymentServiceImpl cardPaymentService;
//
//    @Test
//    void testPaymentSuccess() {
//        // Datos de prueba
//        String cardNumber = "1234567890123456";
//        String cvc = "123";
//        String expirationDate = "12/25";
//        String fullName = "Juan Perez";
//        String destinationIban = "ES1234567890123456789012";
//        BigDecimal amount = new BigDecimal("100.00");
//        String concept = "Pago de prueba";
//
//        // Mock tarjeta
//        CreditCard card = mock(CreditCard.class);
//        when(card.getCvc()).thenReturn(cvc);
//        when(card.getExpirationDate()).thenReturn(expirationDate);
//        when(card.getCardHolderName()).thenReturn(fullName);
//        when(card.isExpired()).thenReturn(false);
//
//        // Mock cuenta origen
//        BankAccount fromAccount = mock(BankAccount.class);
//        when(fromAccount.getBalance()).thenReturn(new BigDecimal("500.00"));
//        when(card.getBankAccount()).thenReturn(fromAccount);
//
//        // Mock cuenta destino
//        BankAccount toAccount = mock(BankAccount.class);
//        when(toAccount.getBalance()).thenReturn(new BigDecimal("200.00"));
//
//        // Configurar repositorios
//        when(creditCardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));
//        when(bankAccountRepository.findByIban(destinationIban)).thenReturn(Optional.of(toAccount));
//
//        // Ejecutar
//        assertDoesNotThrow(() ->
//            cardPaymentService.processCardPayment(
//                cardNumber, expirationDate, cvc, fullName,
//                destinationIban, amount, concept
//            )
//        );
//
//        // Verificar
//        verify(bankAccountRepository, times(2)).save(any(BankAccount.class));
//        verify(bankTransactionService, times(2)).createTransaction(any(), any(), any(), any(), any(), any());
//    }
//
//    @Test
//    void testCardNotFound() {
//        when(creditCardRepository.findByCardNumber(anyString())).thenReturn(Optional.empty());
//
//        assertThrows(IllegalArgumentException.class, () ->
//            cardPaymentService.processCardPayment(
//                "1234567890123456", "12/25", "123", "Juan Perez",
//                "ES1234567890123456789012", new BigDecimal("100"), "Test"
//            )
//        );
//    }
//
//    @Test
//    void testInsufficientFunds() {
//        CreditCard card = mock(CreditCard.class);
//        when(card.getCvc()).thenReturn("123");
//        when(card.getExpirationDate()).thenReturn("12/25");
//        when(card.getCardHolderName()).thenReturn("Juan Perez");
//        when(card.isExpired()).thenReturn(false);
//
//        BankAccount fromAccount = mock(BankAccount.class);
//        when(fromAccount.getBalance()).thenReturn(new BigDecimal("50.00")); // Saldo insuficiente
//        when(card.getBankAccount()).thenReturn(fromAccount);
//
//        when(creditCardRepository.findByCardNumber(anyString())).thenReturn(Optional.of(card));
//
//        assertThrows(IllegalArgumentException.class, () ->
//            cardPaymentService.processCardPayment(
//                "1234567890123456", "12/25", "123", "Juan Perez",
//                "ES1234567890123456789012", new BigDecimal("100"), "Test"
//            )
//        );
//    }
//}