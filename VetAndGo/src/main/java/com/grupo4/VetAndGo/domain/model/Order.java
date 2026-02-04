package com.grupo4.VetAndGo.domain.model;

import java.util.List;

import com.grupo4.VetAndGo.domain.model.enums.OrderState;

public class Order {
  private Long id;
  private List<Product> products;
  private OrderState state;
  private User user;

  public Order(Long id, List<Product> products, OrderState state, User user) {
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

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public OrderState getState() {
    return state;
  }

  public void setState(OrderState state) {
    this.state = state;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
