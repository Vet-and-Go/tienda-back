# Order Class Refactoring Guide

## 🎯 Overview

The Order and OrderItem classes have been **completely refactored** following best practices for domain-driven design and clean architecture. This guide explains the improvements and how to use the new API.

---

## 📊 Architecture Improvement

### Before (Simple Many-to-Many)
```
Order ←→ Product (no quantities, duplicates caused errors)
```

### After (Proper E-Commerce Pattern)
```
Order (1) → (many) OrderItem (many) → (1) Product
         ↓
    Business Logic & Validation
```

---

## 🆕 New Features in OrderItem

### 1. Factory Method Pattern
Instead of complex constructors, use the factory method:

```java
// Old way (error-prone)
OrderItem item = new OrderItem(null, product, 5, product.getFinalPrice(), null);

// New way (clean and safe)
OrderItem item = OrderItem.create(product, 5);
```

### 2. Automatic Calculations
```java
OrderItem item = OrderItem.create(product, 5);
// Automatically sets:
// - unitPrice = product.getFinalPrice()
// - subtotal = unitPrice × quantity (with proper rounding)
```

### 3. Built-in Validation
```java
// Throws IllegalArgumentException for invalid inputs
OrderItem.create(product, 0);      // ❌ Quantity must be > 0
OrderItem.create(product, -5);     // ❌ Quantity must be > 0
OrderItem.create(null, 5);         // ❌ Product cannot be null

item.setUnitPrice(new BigDecimal("-10")); // ❌ Price cannot be negative
```

### 4. Quantity Management
```java
OrderItem item = OrderItem.create(product, 5);

// Increase quantity
item.increaseQuantity(3);  // Now 8 units
System.out.println(item.getQuantity()); // Output: 8

// Decrease quantity
item.decreaseQuantity(2);  // Now 6 units
System.out.println(item.getQuantity()); // Output: 6

// Automatic subtotal recalculation after each change
System.out.println(item.getSubtotal()); // Updated automatically
```

### 5. Product Comparison
```java
OrderItem item1 = OrderItem.create(product1, 2);
OrderItem item2 = OrderItem.create(product2, 3);

if (item1.isSameProduct(product1)) {
    System.out.println("Item contains this product!");
}
```

### 6. Proper equals/hashCode/toString
```java
OrderItem item = OrderItem.create(product, 5);
System.out.println(item);
// Output: OrderItem{id=1, product=Dog Food, quantity=5, unitPrice=41.39, subtotal=206.95}
```

---

## 🎨 New Features in Order

### 1. Immutable Collections
```java
Order order = new Order(...);
List<OrderItem> items = order.getItems(); // Returns unmodifiable list

// This prevents external modification
items.add(newItem); // ❌ Throws UnsupportedOperationException
```

### 2. Smart Item Management
```java
Order order = new Order(...);

// Add item (automatically merges if product exists)
OrderItem item1 = OrderItem.create(product1, 2);
order.addItem(item1);

OrderItem item2 = OrderItem.create(product1, 3);
order.addItem(item2); // Merged! Product1 now has quantity 5

// Remove items
order.removeItem(item);
order.removeItemByProduct(product);
order.clearItems();
```

### 3. Query Methods
```java
Order order = new Order(...);

// Check contents
boolean hasItems = order.hasItems();
boolean hasProduct = order.containsProduct(product);

// Get statistics
int totalItems = order.getTotalItemCount();      // Total quantity of all items
int uniqueProducts = order.getUniqueProductCount(); // Number of different products

// Find specific item
Optional<OrderItem> item = order.findItemByProduct(product);
```

### 4. State Checking
```java
Order order = new Order(...);

// Convenient state checks
if (order.isPending()) {
    // Handle pending order
}

if (order.isProcessed()) {
    // Handle processed order
}

if (order.isDelivered()) {
    // Handle delivered order
}
```

### 5. Recalculation
```java
Order order = new Order(...);

// Manual recalculation (if needed)
order.recalculateTotalAmount();

// Automatic recalculation on modifications
order.addItem(item);     // Total recalculated
order.removeItem(item);  // Total recalculated
order.setItems(newList); // Total recalculated
```

### 6. Proper toString
```java
Order order = new Order(...);
System.out.println(order);
// Output: Order{id=1, itemCount=3, state=PENDING, user=john_doe, orderDate=2026-02-09T09:00:00, totalAmount=257.17}
```

---

## 💡 Usage Examples

### Example 1: Create Order from Product IDs (Current API)
```java
// API receives: {"productIds": [1, 1, 1, 2, 2]}

// Service layer processes:
Map<Long, Long> quantities = productIds.stream()
    .collect(Collectors.groupingBy(id -> id, Collectors.counting()));
// Result: {1=3, 2=2}

// Create OrderItems using factory method:
for (Map.Entry<Long, Long> entry : quantities.entrySet()) {
    Product product = productRepository.findById(entry.getKey());
    OrderItem item = OrderItem.create(product, entry.getValue().intValue());
    items.add(item);
}
```

### Example 2: Modify Order Quantities
```java
Order order = orderRepository.findById(orderId);

// Find item for specific product
Optional<OrderItem> item = order.findItemByProduct(product);

if (item.isPresent()) {
    // Increase quantity
    item.get().increaseQuantity(2);
    
    // Recalculate order total
    order.recalculateTotalAmount();
    
    // Save
    orderRepository.save(order);
}
```

### Example 3: Add Item to Existing Order
```java
Order order = orderRepository.findById(orderId);
Product newProduct = productRepository.findById(productId);

// Create new item
OrderItem newItem = OrderItem.create(newProduct, 3);

// Add to order (merges if product already exists)
order.addItem(newItem);

// Save (total is automatically recalculated)
orderRepository.save(order);
```

### Example 4: Validate Order Before Processing
```java
Order order = orderRepository.findById(orderId);

// Business validations
if (!order.hasItems()) {
    throw new BusinessException("Order has no items");
}

if (!order.isPending()) {
    throw new BusinessException("Only pending orders can be processed");
}

// Process order
order.setState(OrderState.PROCESSED);
orderRepository.save(order);
```

---

## 🔧 API Request/Response Examples

### Create Order with Quantities
**Request:**
```json
POST /api/orders?userId=1
{
  "productIds": [101, 101, 101, 102, 102],
  "state": "PENDING"
}
```

**Response:**
```json
{
  "id": 25,
  "items": [
    {
      "id": 50,
      "product": {
        "id": 101,
        "name": "Dog Food Premium",
        "finalPrice": 41.39
      },
      "quantity": 3,
      "unitPrice": 41.39,
      "subtotal": 124.17
    },
    {
      "id": 51,
      "product": {
        "id": 102,
        "name": "Cat Toy",
        "finalPrice": 15.50
      },
      "quantity": 2,
      "unitPrice": 15.50,
      "subtotal": 31.00
    }
  ],
  "state": "PENDING",
  "user": { "id": 1, "username": "john_doe" },
  "orderDate": "2026-02-09T09:05:23",
  "totalAmount": 155.17
}
```

---

## 🎯 Benefits of This Refactoring

### 1. **Type Safety**
- Factory methods prevent invalid state
- Validation at construction time
- No null pointer exceptions

### 2. **Encapsulation**
- Immutable collections prevent external modification
- Business logic centralized in domain models
- Clear separation of concerns

### 3. **Business Logic in Domain**
- `OrderItem.create()` handles price capture
- `Order.addItem()` handles merging logic
- State checks (`isPending()`, etc.) improve readability

### 4. **Testability**
- Easy to create test instances with factory methods
- Clear method contracts
- Predictable behavior

### 5. **Maintainability**
- Self-documenting code
- Less duplication
- Easier to extend

### 6. **Performance**
- Efficient merging of duplicate products
- Lazy calculation with caching
- Proper BigDecimal rounding

---

## 📋 Validation Rules

### OrderItem Validations
✅ Product cannot be null  
✅ Quantity must be greater than 0  
✅ Unit price cannot be negative  
✅ Subtotal automatically calculated with proper rounding (2 decimals)

### Order Validations
✅ Items list is never null (empty list if no items)  
✅ Total amount recalculated automatically on modifications  
✅ Immutable item list prevents external tampering  

---

## 🚀 Migration Path

If you have existing code using the old pattern:

### Before:
```java
// Manual calculation
BigDecimal unitPrice = product.getFinalPrice();
BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

OrderItemJpaEntity item = new OrderItemJpaEntity();
item.setProduct(productEntity);
item.setQuantity(quantity);
item.setUnitPrice(unitPrice);
item.setSubtotal(subtotal);
```

### After:
```java
// Clean and safe
OrderItem item = OrderItem.create(product, quantity);
// All calculations and validations handled automatically
```

---

## 📚 Design Patterns Used

1. **Factory Method**: `OrderItem.create()`
2. **Builder Pattern**: Fluent setters that return `this`
3. **Value Object**: Immutable-like behavior with validation
4. **Domain-Driven Design**: Business logic in domain models
5. **Aggregate Root**: Order manages OrderItems lifecycle

---

## 🎓 Best Practices Implemented

✅ **Single Responsibility**: Each class has one clear purpose  
✅ **Validation at Boundaries**: Invalid state prevented at construction  
✅ **Immutability**: Collections returned as unmodifiable  
✅ **Null Safety**: Objects.requireNonNull() prevents NPEs  
✅ **Clear Intent**: Method names describe what they do  
✅ **Encapsulation**: Internal state protected from external modification  
✅ **DRY Principle**: Calculation logic centralized  
✅ **Business Logic in Domain**: Not in service layer  

---

## 🔍 Testing Recommendations

### Unit Tests for OrderItem
```java
@Test
void create_WithValidInputs_CreatesOrderItem() {
    Product product = createProduct(41.39);
    OrderItem item = OrderItem.create(product, 5);
    
    assertEquals(5, item.getQuantity());
    assertEquals(new BigDecimal("41.39"), item.getUnitPrice());
    assertEquals(new BigDecimal("206.95"), item.getSubtotal());
}

@Test
void create_WithZeroQuantity_ThrowsException() {
    Product product = createProduct(41.39);
    assertThrows(IllegalArgumentException.class, () -> {
        OrderItem.create(product, 0);
    });
}

@Test
void increaseQuantity_UpdatesSubtotal() {
    Product product = createProduct(10.00);
    OrderItem item = OrderItem.create(product, 2);
    
    item.increaseQuantity(3);
    
    assertEquals(5, item.getQuantity());
    assertEquals(new BigDecimal("50.00"), item.getSubtotal());
}
```

### Unit Tests for Order
```java
@Test
void addItem_WithNewProduct_AddsItem() {
    Order order = createOrder();
    OrderItem item = OrderItem.create(product, 2);
    
    order.addItem(item);
    
    assertEquals(1, order.getUniqueProductCount());
    assertEquals(2, order.getTotalItemCount());
}

@Test
void addItem_WithExistingProduct_MergesQuantities() {
    Order order = createOrder();
    OrderItem item1 = OrderItem.create(product, 2);
    OrderItem item2 = OrderItem.create(product, 3);
    
    order.addItem(item1);
    order.addItem(item2);
    
    assertEquals(1, order.getUniqueProductCount());
    assertEquals(5, order.getTotalItemCount());
}
```

---

**Refactored by:** GitHub Copilot CLI  
**Date:** 2026-02-09  
**Version:** 2.0 (Production Ready)
