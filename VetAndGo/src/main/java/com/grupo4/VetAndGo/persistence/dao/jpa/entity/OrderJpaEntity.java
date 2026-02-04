package com.grupo4.VetAndGo.persistence.dao.jpa.entity;

import java.io.Serializable;
import java.util.List;

import com.grupo4.VetAndGo.domain.model.enums.OrderState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderJpaEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToMany
  @JoinTable(name = "order_products", // name of the join table
      joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
  List<ProductJpaEntity> products;

  @Enumerated(EnumType.STRING)
  @Column(name = "state")
  OrderState state;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  UserJpaEntity user;

  public OrderJpaEntity() {
  }

  public OrderJpaEntity(Long id, List<ProductJpaEntity> products, OrderState state, UserJpaEntity user) {
    this.id = id;
    this.products = products;
    this.state = state;
    this.user = user;
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

  public List<ProductJpaEntity> getProducts() {
    return products;
  }

  public void setProducts(List<ProductJpaEntity> products) {
    this.products = products;
  }

}
