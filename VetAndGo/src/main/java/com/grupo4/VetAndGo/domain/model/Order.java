package com.grupo4.VetAndGo.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.grupo4.VetAndGo.domain.model.enums.OrderState;

public class Order {
  private Long id;
  private List<OrderItem> items;
  private OrderState state;
  private User user;
  private LocalDateTime orderDate;
  private BigDecimal totalAmount;

  public Order(Long id, List<OrderItem> items, OrderState state, User user, LocalDateTime orderDate, BigDecimal totalAmount) {
    this.id = id;
    this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
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

  public List<OrderItem> getItems() {
    return Collections.unmodifiableList(items);
  }

  public void setItems(List<OrderItem> items) {
    this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    this.totalAmount = calculateTotalAmount();
  }

  public void addItem(OrderItem item) {
    Objects.requireNonNull(item, "Order item cannot be null");
    
    Optional<OrderItem> existingItem = findItemByProduct(item.getProduct());
    
    if (existingItem.isPresent()) {
      existingItem.get().increaseQuantity(item.getQuantity());
    } else {
      this.items.add(item);
    }
    
    this.totalAmount = calculateTotalAmount();
  }

  public void removeItem(OrderItem item) {
    this.items.remove(item);
    this.totalAmount = calculateTotalAmount();
  }

  public void removeItemByProduct(Product product) {
    this.items.removeIf(item -> item.isSameProduct(product));
    this.totalAmount = calculateTotalAmount();
  }

  public void clearItems() {
    this.items.clear();
    this.totalAmount = BigDecimal.ZERO;
  }

  public Optional<OrderItem> findItemByProduct(Product product) {
    return items.stream()
        .filter(item -> item.isSameProduct(product))
        .findFirst();
  }

  public int getTotalItemCount() {
    return items.stream()
        .mapToInt(OrderItem::getQuantity)
        .sum();
  }

  public int getUniqueProductCount() {
    return items.size();
  }

  public boolean hasItems() {
    return !items.isEmpty();
  }

  public boolean containsProduct(Product product) {
    return findItemByProduct(product).isPresent();
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

  public BigDecimal calculateTotalAmount() {
    if (items == null || items.isEmpty()) {
      return BigDecimal.ZERO;
    }
    return items.stream()
        .map(OrderItem::getSubtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void recalculateTotalAmount() {
    this.totalAmount = calculateTotalAmount();
  }

  public boolean isPending() {
    return OrderState.PENDING.equals(state);
  }

  public boolean isProcessed() {
    return OrderState.PROCESSED.equals(state);
  }

  public boolean isDelivered() {
    return OrderState.DELIVERED.equals(state);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Order)) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", itemCount=" + items.size() +
        ", state=" + state +
        ", user=" + (user != null ? user.getUsername() : "null") +
        ", orderDate=" + orderDate +
        ", totalAmount=" + totalAmount +
        '}';
  }
}
