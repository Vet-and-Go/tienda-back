package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.controller.dto.CardPaymentRequest;
import com.grupo4.VetAndGo.domain.dto.OrderDto;
import com.grupo4.VetAndGo.domain.exception.ResourceNotFoundException;
import com.grupo4.VetAndGo.domain.model.Category;
import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.domain.model.enums.OrderState;
import com.grupo4.VetAndGo.domain.repository.OrderRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.domain.service.PaymentService;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderItemJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private UserJpaEntity userEntity;
    private OrderJpaEntity orderEntity;
    private Product product;
    private OrderItemJpaEntity orderItemEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserJpaEntity(); // Assuming default constructor
        userEntity.setId(1L);
        
        Category category = new Category(1L, "Cat", "Desc");
        product = new Product(1L, "Prod", category, "Desc", BigDecimal.TEN, 10, BigDecimal.ZERO, BigDecimal.TEN, "img");

        orderItemEntity = new OrderItemJpaEntity();
        orderItemEntity.setId(1L);
        orderItemEntity.setProduct(new ProductJpaEntity()); // Simplified
        orderItemEntity.setQuantity(1);
        orderItemEntity.setUnitPrice(BigDecimal.TEN);
        orderItemEntity.setSubtotal(BigDecimal.TEN);

        orderEntity = new OrderJpaEntity();
        orderEntity.setId(1L);
        orderEntity.setUser(userEntity);
        orderEntity.setState(OrderState.PENDING);
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setItems(new java.util.ArrayList<>(List.of(orderItemEntity)));
        orderEntity.setTotalAmount(BigDecimal.TEN);
    }

    @Test
    void getAll_ShouldReturnListOfOrderDto_WhenOrdersExist() {
        when(orderRepository.getAll(1L)).thenReturn(List.of(orderEntity));

        List<OrderDto> result = orderService.getAll(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(orderRepository).getAll(1L);
    }

    @Test
    void getAll_ShouldThrowResourceNotFoundException_WhenNoOrdersExist() {
        when(orderRepository.getAll(1L)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getAll(1L));
    }

    @Test
    void getById_ShouldReturnOrderDto_WhenOrderExists() {
        when(orderRepository.getById(1L)).thenReturn(Optional.of(orderEntity));

        OrderDto result = orderService.getById(1L);

        assertNotNull(result);
        assertEquals(orderEntity.getId(), result.id());
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException_WhenOrderDoesNotExist() {
        when(orderRepository.getById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getById(1L));
    }
    
    // Test create
    @Test
    void create_ShouldReturnOrderDto() {
        Map<Long, Integer> productQuantities = Map.of(1L, 1);
        
        when(userRepository.findById(1L)).thenReturn(userEntity);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(OrderJpaEntity.class))).thenAnswer(invocation -> {
            OrderJpaEntity saved = invocation.getArgument(0);
            saved.setId(1L); // Simulate ID generation
            return saved;
        });

        OrderDto result = orderService.create(productQuantities, OrderState.PENDING, 1L);

        assertNotNull(result);
        assertEquals(OrderState.PENDING, result.state());
        verify(userRepository).findById(1L);
        verify(productRepository).findById(1L);
        verify(orderRepository).save(any(OrderJpaEntity.class));
    }
    
    // Test checkout
    @Test
    void checkout_ShouldProcessPaymentAndReturnOrderDto() {
         Map<Long, Integer> productQuantities = Map.of(1L, 1);

         // Mocks for create() part
         when(userRepository.findById(1L)).thenReturn(userEntity);
         when(productRepository.findById(1L)).thenReturn(Optional.of(product));
         when(orderRepository.save(any(OrderJpaEntity.class))).thenAnswer(invocation -> {
            OrderJpaEntity saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
         });
         
         // Mocks for processPayment
         doNothing().when(paymentService).processPayment(any(CardPaymentRequest.class));
         
         // Mocks for changeState part inside checkout (it calls getById -> save)
         // Note: changeState calls orderRepository.getById(id)
         // But wait, changeState calls orderRepository.save again.
         
         // Mocking getById for the changeState call and the final return call
         when(orderRepository.getById(1L)).thenReturn(Optional.of(orderEntity));
         
         OrderDto result = orderService.checkout(productQuantities, 1L, "1234", "12/24", "123", "Name", "login", "token", "concept");
         
         assertNotNull(result);
         verify(paymentService).processPayment(any(CardPaymentRequest.class));
    }

    @Test
    void update_ShouldReturnUpdatedOrderDto_WhenOrderExists() {
        Map<Long, Integer> productQuantities = Map.of(1L, 2);
        
        when(orderRepository.getById(1L)).thenReturn(Optional.of(orderEntity));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(OrderJpaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderDto result = orderService.update(1L, productQuantities, OrderState.PROCESSED);

        assertNotNull(result);
        assertEquals(OrderState.PROCESSED, result.state());
        verify(orderRepository).getById(1L);
        verify(productRepository).findById(1L);
        verify(orderRepository).save(any(OrderJpaEntity.class));
    }

    @Test
    void update_ShouldThrowResourceNotFoundException_WhenOrderDoesNotExist() {
        Map<Long, Integer> productQuantities = Map.of(1L, 1);
        when(orderRepository.getById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.update(1L, productQuantities, OrderState.PENDING));
    }
    
    @Test
    void delete_ShouldDeleteOrder_WhenOrderExists() {
        when(orderRepository.getById(1L)).thenReturn(Optional.of(orderEntity));
        doNothing().when(orderRepository).delete(1L);

        orderService.delete(1L);

        verify(orderRepository).getById(1L);
        verify(orderRepository).delete(1L);
    }

    @Test
    void delete_ShouldThrowResourceNotFoundException_WhenOrderDoesNotExist() {
        when(orderRepository.getById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.delete(1L));
        verify(orderRepository, never()).delete(anyLong());
    }

    @Test
    void changeState_ShouldUpdateState_WhenOrderExists() {
        when(orderRepository.getById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(any(OrderJpaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        orderService.changeState(1L, OrderState.PROCESSED);

        verify(orderRepository).getById(1L);
        verify(orderRepository).save(any(OrderJpaEntity.class));
        assertEquals(OrderState.PROCESSED, orderEntity.getState());
    }
    
    @Test
    void changeState_ShouldThrowResourceNotFoundException_WhenOrderDoesNotExist() {
        when(orderRepository.getById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.changeState(1L, OrderState.PROCESSED));
        verify(orderRepository, never()).save(any(OrderJpaEntity.class));
    }

    @Test
    void getProductsFromOrder_ShouldReturnListOfProductDto_WhenOrderExists() {
        when(orderRepository.getById(1L)).thenReturn(Optional.of(orderEntity));

        // Ensure orderEntity has items with products
        // orderEntity setup in setUp() already has one item with a product
        // However, the product inside orderItemEntity might be empty/mocked differently
        // Let's ensure the product entity inside matches what mapper expects
        ProductJpaEntity productEntity = new ProductJpaEntity();
        productEntity.setId(1L);
        productEntity.setName("Prod");
        productEntity.setCategory(new com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity(1L, "Cat", "Desc")); 
        productEntity.setBasePrice(BigDecimal.TEN);
        
        orderItemEntity.setProduct(productEntity);

        List<com.grupo4.VetAndGo.domain.dto.ProductDto> result = orderService.getProductsFromOrder(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        // verify(orderRepository).getById(1L); // Implicitly verified
    }
    
    @Test
    void getProductsFromOrder_ShouldThrowResourceNotFoundException_WhenOrderDoesNotExist() {
        when(orderRepository.getById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getProductsFromOrder(1L));
    }
}
