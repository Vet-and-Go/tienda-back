package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.model.enums.OrderState;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"})
@Import(OrderJpaDaoImpl.class)
class OrderJpaDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderJpaDaoImpl orderJpaDao;

    private UserJpaEntity user;

    @BeforeEach
    void setUp() {
        user = new UserJpaEntity(null, "ordertestuser", "pass", Role.USER);
        entityManager.persist(user);
    }

    @Test
    void findAll_ShouldReturnAllOrders() {
        createAndPersistOrder(user, OrderState.PENDING);
        createAndPersistOrder(user, OrderState.PROCESSED);
        entityManager.flush();

        List<OrderJpaEntity> result = orderJpaDao.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findById_ShouldReturnOrder_WhenExists() {
        OrderJpaEntity order = createAndPersistOrder(user, OrderState.PENDING);

        Optional<OrderJpaEntity> result = orderJpaDao.findById(order.getId());

        assertTrue(result.isPresent());
        assertEquals(order.getId(), result.get().getId());
    }

    @Test
    void insert_ShouldPersistOrder() {
        OrderJpaEntity order = new OrderJpaEntity();
        order.setUser(user);
        order.setState(OrderState.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.TEN);

        OrderJpaEntity saved = orderJpaDao.insert(order);

        assertNotNull(saved.getId());
        assertEquals(OrderState.PENDING, entityManager.find(OrderJpaEntity.class, saved.getId()).getState());
    }

    @Test
    void update_ShouldUpdateOrder() {
        OrderJpaEntity order = createAndPersistOrder(user, OrderState.PENDING);
        
        order.setState(OrderState.DELIVERED);
        orderJpaDao.update(order);

        assertEquals(OrderState.DELIVERED, entityManager.find(OrderJpaEntity.class, order.getId()).getState());
    }

    @Test
    void deleteById_ShouldRemoveOrder() {
        OrderJpaEntity order = createAndPersistOrder(user, OrderState.PENDING);

        orderJpaDao.deleteById(order.getId());

        assertNull(entityManager.find(OrderJpaEntity.class, order.getId()));
    }

    private OrderJpaEntity createAndPersistOrder(UserJpaEntity user, OrderState state) {
        OrderJpaEntity order = new OrderJpaEntity();
        order.setUser(user);
        order.setState(state);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.TEN);
        return entityManager.persistFlushFind(order);
    }
}
