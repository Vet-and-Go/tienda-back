package com.grupo4.VetAndGo.persistence.dao.jpa.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.grupo4.VetAndGo.domain.model.enums.OrderState;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderJpaEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  List<OrderItemJpaEntity> items = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(name = "state")
  OrderState state;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id")
  UserJpaEntity user;

  @Column(name = "order_date", nullable = false)
  LocalDateTime orderDate;

  @Column(name = "total_amount", nullable = false)
  BigDecimal totalAmount;

  public OrderJpaEntity() {
  }

  public OrderJpaEntity(Long id, List<OrderItemJpaEntity> items, OrderState state, UserJpaEntity user, LocalDateTime orderDate, BigDecimal totalAmount) {
    this.id = id;
    this.items = items;
    this.state = state;
    this.user = user;
    this.orderDate = orderDate;
    this.totalAmount = totalAmount;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OrderState getState() {
    return state;
  }

  public void setState(OrderState state) {
    this.state = state;
  }

  public UserJpaEntity getUser() {
    return user;
  }

  public void setUser(UserJpaEntity user) {
    this.user = user;
  }

  public List<OrderItemJpaEntity> getItems() {
    return items;
  }

  public void setItems(List<OrderItemJpaEntity> items) {
    this.items = items;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

}
