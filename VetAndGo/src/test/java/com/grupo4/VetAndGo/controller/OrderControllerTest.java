package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.controller.webmodel.request.CheckoutRequest;
import com.grupo4.VetAndGo.controller.webmodel.request.Order.OrderInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.Order.OrderItemRequest;
import com.grupo4.VetAndGo.controller.webmodel.request.Order.OrderUpdate;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.dto.OrderDto;
import com.grupo4.VetAndGo.domain.dto.OrderItemDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.model.enums.OrderState;
import com.grupo4.VetAndGo.domain.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import com.grupo4.VetAndGo.spring.AuthFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthFilter.class))
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDto orderDto;
    private UserDto userDto;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        CategoryDto categoryDto = new CategoryDto(1L, "Category1", "Description1");
        
        productDto = new ProductDto(
                1L,
                "Product1",
                categoryDto,
                "Description1",
                new BigDecimal("10.00"),
                100,
                new BigDecimal("0.0"),
                new BigDecimal("10.00"),
                "image.jpg"
        );

        userDto = new UserDto(1L, "user1", "password", Role.USER);

        OrderItemDto orderItemDto = new OrderItemDto(
                1L,
                productDto,
                2,
                new BigDecimal("10.00"),
                new BigDecimal("20.00")
        );

        orderDto = new OrderDto(
                1L,
                List.of(orderItemDto),
                OrderState.PENDING,
                userDto,
                LocalDateTime.now(),
                new BigDecimal("20.00")
        );
    }

    @Test
    @WithMockUser
    void getAllOrders_ShouldReturnOrderList() throws Exception {
        when(orderService.getAll(any())).thenReturn(List.of(orderDto));

        mockMvc.perform(get("/api/orders")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].totalAmount").value(20.00));
    }

    @Test
    @WithMockUser
    void getOrderById_ShouldReturnOrder() throws Exception {
        when(orderService.getById(1L)).thenReturn(orderDto);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(20.00));
    }

    @Test
    @WithMockUser
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        OrderItemRequest itemRequest = new OrderItemRequest(1L, 2);
        OrderInsert orderInsert = new OrderInsert(
                List.of(itemRequest),
                OrderState.PENDING
        );

        when(orderService.create(any(), any(), anyLong())).thenReturn(orderDto);

        mockMvc.perform(post("/api/orders")
                        .with(csrf())
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderInsert)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser
    void updateOrder_ShouldReturnUpdatedOrder() throws Exception {
        OrderItemRequest itemRequest = new OrderItemRequest(1L, 5);
        OrderUpdate orderUpdate = new OrderUpdate(
                1L,
                List.of(itemRequest),
                OrderState.PROCESSED
        );

        OrderDto updatedOrder = new OrderDto(
                1L,
                orderDto.items(),
                OrderState.PROCESSED,
                userDto,
                LocalDateTime.now(),
                new BigDecimal("50.00")
        );

        when(orderService.update(anyLong(), any(), any())).thenReturn(updatedOrder);

        mockMvc.perform(put("/api/orders/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("PROCESSED"));
    }

    @Test
    @WithMockUser
    void deleteOrder_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/orders/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
    
    @Test
    @WithMockUser
    void checkout_ShouldReturnSuccess() throws Exception {
        CheckoutRequest checkoutRequest = new CheckoutRequest(
                List.of(new OrderItemRequest(1L, 1)),
                "1234567812345678",
                "12/26",
                "123",
                "Full Name",
                "login",
                "token",
                "Purchase"
        );
        
        when(orderService.checkout(any(), anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(orderDto);
            
        mockMvc.perform(post("/api/orders/checkout")
                .with(csrf())
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.paymentStatus").value("SUCCESS"));
    }
}
