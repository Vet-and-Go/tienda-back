# Order & OrderItem - Code Examples

## Quick Reference for Common Tasks

### 1️⃣ Creating OrderItems

```java
// ✅ RECOMMENDED: Use factory method
OrderItem item = OrderItem.create(product, 5);

// ❌ AVOID: Direct constructor (more complex, error-prone)
OrderItem item = new OrderItem(null, product, 5, product.getFinalPrice(), null);
```

### 2️⃣ Adding Items to Order

```java
Order order = new Order(...);

// Add new item
OrderItem item = OrderItem.create(product, 3);
order.addItem(item);

// Add another instance of same product (automatically merges)
OrderItem moreOfSame = OrderItem.create(product, 2);
order.addItem(moreOfSame); // Now has 5 units of product
```

### 3️⃣ Managing Quantities

```java
OrderItem item = OrderItem.create(product, 5);

// Increase quantity
item.increaseQuantity(3);  // Now 8
System.out.println(item.getSubtotal()); // Automatically recalculated

// Decrease quantity
item.decreaseQuantity(2);  // Now 6
System.out.println(item.getSubtotal()); // Automatically recalculated

// Direct set (with validation)
item.setQuantity(10);  // ✅ Valid
item.setQuantity(0);   // ❌ Throws IllegalArgumentException
item.setQuantity(-5);  // ❌ Throws IllegalArgumentException
```

### 4️⃣ Querying Order Contents

```java
Order order = orderService.getById(orderId);

// Check if order has items
if (order.hasItems()) {
    System.out.println("Order has items");
}

// Get statistics
int totalUnits = order.getTotalItemCount();      // e.g., 10 (total units)
int uniqueProducts = order.getUniqueProductCount(); // e.g., 3 (different products)

// Check for specific product
if (order.containsProduct(product)) {
    System.out.println("Order contains this product");
}

// Find specific item
Optional<OrderItem> item = order.findItemByProduct(product);
if (item.isPresent()) {
    System.out.println("Found: " + item.get().getQuantity() + " units");
}
```

### 5️⃣ Removing Items

```java
Order order = orderService.getById(orderId);

// Remove specific item
OrderItem itemToRemove = ...;
order.removeItem(itemToRemove);

// Remove by product
order.removeItemByProduct(product);

// Clear all items
order.clearItems();
```

### 6️⃣ Checking Order State

```java
Order order = orderService.getById(orderId);

// Convenient state checks
if (order.isPending()) {
    // Can be modified
    System.out.println("Order is pending");
}

if (order.isProcessed()) {
    // In progress
    System.out.println("Order is being processed");
}

if (order.isDelivered()) {
    // Complete
    System.out.println("Order has been delivered");
}

// Or use direct comparison
if (order.getState() == OrderState.PENDING) {
    // ...
}
```

### 7️⃣ Service Layer - Creating Orders

```java
// In OrderServiceImpl.java

@Override
public OrderDto create(List<Long> productIds, OrderState state, Long userId) {
    UserJpaEntity user = userRepository.findById(userId);
    
    // Count quantities automatically
    Map<Long, Long> quantities = productIds.stream()
        .collect(Collectors.groupingBy(id -> id, Collectors.counting()));
    
    // Create order
    OrderJpaEntity orderEntity = new OrderJpaEntity();
    orderEntity.setState(state != null ? state : OrderState.PENDING);
    orderEntity.setUser(user);
    orderEntity.setOrderDate(LocalDateTime.now());
    
    // Create items using helper method
    List<OrderItemJpaEntity> items = createOrderItems(orderEntity, quantities);
    
    // Calculate total
    BigDecimal total = items.stream()
        .map(OrderItemJpaEntity::getSubtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    orderEntity.setItems(items);
    orderEntity.setTotalAmount(total);
    
    return toDto(orderRepository.save(orderEntity));
}

private List<OrderItemJpaEntity> createOrderItems(
    OrderJpaEntity order, 
    Map<Long, Long> quantities) {
    
    List<OrderItemJpaEntity> items = new ArrayList<>();
    
    for (Map.Entry<Long, Long> entry : quantities.entrySet()) {
        Product product = productRepository.findById(entry.getKey())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        // Use domain model factory method
        OrderItem orderItem = OrderItem.create(product, entry.getValue().intValue());
        
        // Convert to entity
        OrderItemJpaEntity itemEntity = new OrderItemJpaEntity();
        itemEntity.setOrder(order);
        itemEntity.setProduct(toEntity(product));
        itemEntity.setQuantity(orderItem.getQuantity());
        itemEntity.setUnitPrice(orderItem.getUnitPrice());
        itemEntity.setSubtotal(orderItem.getSubtotal());
        
        items.add(itemEntity);
    }
    
    return items;
}
```

### 8️⃣ Validation Examples

```java
// OrderItem validation
try {
    OrderItem item = OrderItem.create(product, 0);
} catch (IllegalArgumentException e) {
    // "Quantity must be greater than 0"
}

try {
    OrderItem item = OrderItem.create(null, 5);
} catch (NullPointerException e) {
    // "Product cannot be null"
}

try {
    OrderItem item = OrderItem.create(product, 5);
    item.setUnitPrice(new BigDecimal("-10"));
} catch (IllegalArgumentException e) {
    // "Unit price cannot be negative"
}

try {
    OrderItem item = OrderItem.create(product, 5);
    item.decreaseQuantity(10);
} catch (IllegalArgumentException e) {
    // "Resulting quantity would be zero or negative"
}
```

### 9️⃣ Comparing Products in Items

```java
OrderItem item1 = OrderItem.create(product1, 2);
OrderItem item2 = OrderItem.create(product2, 3);
OrderItem item3 = OrderItem.create(product1, 5);

// Check if items have same product
if (item1.isSameProduct(product1)) {
    System.out.println("Item1 contains product1");
}

if (item1.isSameProduct(product2)) {
    System.out.println("Won't print - different products");
}

// Using in streams
List<OrderItem> itemsWithProduct = order.getItems().stream()
    .filter(item -> item.isSameProduct(product))
    .collect(Collectors.toList());
```

### 🔟 Immutable Collections

```java
Order order = orderService.getById(orderId);

// Get items returns unmodifiable list
List<OrderItem> items = order.getItems();

// ❌ This will throw UnsupportedOperationException
try {
    items.add(OrderItem.create(product, 1));
} catch (UnsupportedOperationException e) {
    System.out.println("Cannot modify directly!");
}

// ✅ Use order methods instead
order.addItem(OrderItem.create(product, 1));

// ✅ Or set entire new list
List<OrderItem> newItems = new ArrayList<>();
newItems.add(OrderItem.create(product1, 2));
newItems.add(OrderItem.create(product2, 3));
order.setItems(newItems);
```

### 1️⃣1️⃣ Calculating Totals

```java
Order order = orderService.getById(orderId);

// Automatic calculation when items change
order.addItem(OrderItem.create(product, 2));
// totalAmount automatically updated

// Manual recalculation (if needed)
order.recalculateTotalAmount();

// Calculate method (doesn't update field)
BigDecimal calculatedTotal = order.calculateTotalAmount();
System.out.println("Calculated total: " + calculatedTotal);
System.out.println("Stored total: " + order.getTotalAmount());
```

### 1️⃣2️⃣ Complete Example: Building an Order

```java
// Create order
Order order = new Order(
    null,                    // id will be generated
    new ArrayList<>(),       // empty items initially
    OrderState.PENDING,      // initial state
    user,                    // user who placed order
    LocalDateTime.now(),     // order date
    BigDecimal.ZERO          // initial total
);

// Add items one by one
Product product1 = productRepository.findById(1L).get();
Product product2 = productRepository.findById(2L).get();
Product product3 = productRepository.findById(3L).get();

order.addItem(OrderItem.create(product1, 3));  // 3 units of product 1
order.addItem(OrderItem.create(product2, 2));  // 2 units of product 2
order.addItem(OrderItem.create(product3, 1));  // 1 unit of product 3

// Add more of product 1 (will merge with existing)
order.addItem(OrderItem.create(product1, 2));  // Now 5 units of product 1

// Check statistics
System.out.println("Unique products: " + order.getUniqueProductCount()); // 3
System.out.println("Total items: " + order.getTotalItemCount());          // 8
System.out.println("Total amount: " + order.getTotalAmount());

// Save order
orderRepository.save(order);
```

### 1️⃣3️⃣ Testing Examples

```java
@Test
void orderItem_create_setsCorrectValues() {
    // Given
    Product product = createProductWithPrice(new BigDecimal("10.00"));
    
    // When
    OrderItem item = OrderItem.create(product, 5);
    
    // Then
    assertEquals(5, item.getQuantity());
    assertEquals(new BigDecimal("10.00"), item.getUnitPrice());
    assertEquals(new BigDecimal("50.00"), item.getSubtotal());
}

@Test
void orderItem_increaseQuantity_updatesSubtotal() {
    // Given
    Product product = createProductWithPrice(new BigDecimal("10.00"));
    OrderItem item = OrderItem.create(product, 2);
    
    // When
    item.increaseQuantity(3);
    
    // Then
    assertEquals(5, item.getQuantity());
    assertEquals(new BigDecimal("50.00"), item.getSubtotal());
}

@Test
void order_addItem_mergesSameProduct() {
    // Given
    Order order = createEmptyOrder();
    Product product = createProduct();
    
    // When
    order.addItem(OrderItem.create(product, 2));
    order.addItem(OrderItem.create(product, 3));
    
    // Then
    assertEquals(1, order.getUniqueProductCount());
    assertEquals(5, order.getTotalItemCount());
}

@Test
void order_getItems_returnsUnmodifiableList() {
    // Given
    Order order = createOrderWithItems();
    List<OrderItem> items = order.getItems();
    
    // When/Then
    assertThrows(UnsupportedOperationException.class, () -> {
        items.add(OrderItem.create(createProduct(), 1));
    });
}
```

---

## 💡 Best Practices

### ✅ DO:
- Use `OrderItem.create()` factory method
- Use `order.addItem()` to add items
- Use query methods (`hasItems()`, `containsProduct()`, etc.)
- Use state check methods (`isPending()`, `isProcessed()`, etc.)
- Let the domain model handle calculations
- Validate inputs at construction time

### ❌ DON'T:
- Modify the list returned by `order.getItems()` directly
- Calculate subtotals manually (let OrderItem do it)
- Skip validation (use factory methods)
- Directly set quantities without validation
- Put business logic in service layer (belongs in domain)

---

## 🎯 Quick Decision Guide

**Need to create an OrderItem?**  
→ Use `OrderItem.create(product, quantity)`

**Need to add item to order?**  
→ Use `order.addItem(item)`

**Need to change quantity?**  
→ Use `item.increaseQuantity()` or `item.decreaseQuantity()`

**Need to find an item?**  
→ Use `order.findItemByProduct(product)`

**Need to check state?**  
→ Use `order.isPending()`, `order.isProcessed()`, etc.

**Need statistics?**  
→ Use `order.getTotalItemCount()`, `order.getUniqueProductCount()`

---

**Last Updated:** 2026-02-09  
**Version:** 2.0
