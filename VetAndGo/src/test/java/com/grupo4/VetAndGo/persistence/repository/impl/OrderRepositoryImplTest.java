package com.grupo4.VetAndGo.persistence.repository.impl;

import com.grupo4.VetAndGo.domain.model.enums.OrderState;
import com.grupo4.VetAndGo.persistence.dao.jpa.OrderJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryImplTest {

    @Mock
    private OrderJpaDao orderJpaDao;

    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    @Test
    void getAll_ShouldReturnFilteredOrders() {
        UserJpaEntity u1 = new UserJpaEntity(); u1.setId(1L);
        UserJpaEntity u2 = new UserJpaEntity(); u2.setId(2L);
        
        OrderJpaEntity o1 = new OrderJpaEntity(); o1.setUser(u1);
        OrderJpaEntity o2 = new OrderJpaEntity(); o2.setUser(u2);
        
        when(orderJpaDao.findAll()).thenReturn(List.of(o1, o2));

        List<OrderJpaEntity> result = orderRepository.getAll(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUser().getId());
    }

    @Test
    void getAll_ShouldReturnAllOrders_WhenUserIdIsNull() {
        when(orderJpaDao.findAll()).thenReturn(List.of(new OrderJpaEntity()));
        assertEquals(1, orderRepository.getAll(null).size());
    }

    @Test
    void getById_ShouldDelegate() {
        orderRepository.getById(1L);
        verify(orderJpaDao).findById(1L);
    }

    @Test
    void save_ShouldDelegateToInsert_WhenIdIsNull() {
        OrderJpaEntity order = new OrderJpaEntity(); // ID is null
        when(orderJpaDao.insert(order)).thenReturn(order);
        
        orderRepository.save(order);
        verify(orderJpaDao).insert(order);
    }

    @Test
    void save_ShouldDelegateToUpdate_WhenIdIsNotNull() {
        OrderJpaEntity order = new OrderJpaEntity();
        order.setId(1L);
        when(orderJpaDao.update(order)).thenReturn(order);
        
        orderRepository.save(order);
        verify(orderJpaDao).update(order);
    }

    @Test
    void delete_ShouldDelegate() {
        orderRepository.delete(1L);
        verify(orderJpaDao).deleteById(1L);
    }
}
