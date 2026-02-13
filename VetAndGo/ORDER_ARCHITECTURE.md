# Order Architecture Overview

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Client/Frontend                          │
│                    (JSON Requests)                           │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                  Controller Layer                            │
│  OrderController.java                                        │
│  - POST   /api/orders                                        │
│  - GET    /api/orders                                        │
│  - GET    /api/orders/{id}                                   │
│  - PUT    /api/orders/{id}                                   │
│  - PATCH  /api/orders/{id}/state/{state}                    │
│  - DELETE /api/orders/{id}                                   │
│                                                              │
│  Web Models:                                                 │
│  • OrderInsert (productIds, state)                          │
│  • OrderUpdate (id, productIds, state)                      │
│  • OrderResponse (id, items, state, user, date, total)      │
│  • OrderItemResponse (id, product, quantity, price, sub)    │
└──────────────────────┬──────────────────────────────────────┘
                       │
                  (Mappers)
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                   Service Layer                              │
│  OrderServiceImpl.java                                       │
│  - getAll(userId)                                            │
│  - getById(id)                                               │
│  - create(productIds, state, userId)                        │
│  - update(id, productIds, state)                            │
│  - changeState(id, state)                                    │
│  - delete(id)                                                │
│  - getProductsFromOrder(orderId)                            │
│                                                              │
│  Helper Methods:                                             │
│  - createOrderItems(order, quantities)                      │
│                                                              │
│  DTOs:                                                       │
│  • OrderDto (id, items, state, user, date, total)           │
│  • OrderItemDto (id, product, quantity, price, subtotal)    │
└──────────────────────┬──────────────────────────────────────┘
                       │
                  (Mappers)
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                   Domain Layer                               │
│                                                              │
│  Order.java (Aggregate Root)                                 │
│  ├─ id: Long                                                 │
│  ├─ items: List<OrderItem>                                   │
│  ├─ state: OrderState                                        │
│  ├─ user: User                                               │
│  ├─ orderDate: LocalDateTime                                 │
│  └─ totalAmount: BigDecimal                                  │
│                                                              │
│  Business Methods:                                           │
│  ├─ addItem(OrderItem)          → Smart merging             │
│  ├─ removeItem(OrderItem)       → Remove specific item      │
│  ├─ removeItemByProduct(Product)→ Remove by product         │
│  ├─ clearItems()                → Remove all items          │
│  ├─ findItemByProduct(Product)  → Query item                │
│  ├─ hasItems()                  → Check if has items        │
│  ├─ containsProduct(Product)    → Check product presence    │
│  ├─ getTotalItemCount()         → Total quantity            │
│  ├─ getUniqueProductCount()     → Unique products           │
│  ├─ calculateTotalAmount()      → Calculate total           │
│  ├─ recalculateTotalAmount()    → Force recalculation       │
│  ├─ isPending()                 → State check               │
│  ├─ isProcessed()               → State check               │
│  └─ isDelivered()               → State check               │
│                                                              │
│  OrderItem.java (Value Object)                               │
│  ├─ id: Long                                                 │
│  ├─ product: Product                                         │
│  ├─ quantity: Integer                                        │
│  ├─ unitPrice: BigDecimal                                    │
│  └─ subtotal: BigDecimal                                     │
│                                                              │
│  Factory Method:                                             │
│  └─ create(Product, quantity)    → Smart creation           │
│                                                              │
│  Business Methods:                                           │
│  ├─ increaseQuantity(amount)     → Increase qty             │
│  ├─ decreaseQuantity(amount)     → Decrease qty             │
│  ├─ calculateSubtotal()          → Calculate subtotal       │
│  └─ isSameProduct(Product)       → Product comparison       │
│                                                              │
│  Validations:                                                │
│  ├─ Product cannot be null                                   │
│  ├─ Quantity must be > 0                                     │
│  └─ Unit price cannot be negative                            │
│                                                              │
│  OrderState.java (Enum)                                      │
│  ├─ PENDING                                                  │
│  ├─ PROCESSED                                                │
│  └─ DELIVERED                                                │
└──────────────────────┬──────────────────────────────────────┘
                       │
                  (Mappers)
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                Repository Layer                              │
│  OrderRepositoryImpl.java                                    │
│  - getAll(userId)                                            │
│  - getById(id)                                               │
│  - save(OrderJpaEntity)                                      │
│  - delete(id)                                                │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                    DAO Layer                                 │
│  OrderJpaDaoImpl.java                                        │
│  - findAll()                                                 │
│  - findById(id)                                              │
│  - insert(entity)                                            │
│  - update(entity)                                            │
│  - deleteById(id)                                            │
│  - count()                                                   │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                Persistence Layer                             │
│                                                              │
│  OrderJpaEntity.java                                         │
│  @Entity @Table(name = "orders")                             │
│  ├─ id: Long                                                 │
│  ├─ items: List<OrderItemJpaEntity>  @OneToMany             │
│  ├─ state: OrderState                @Enumerated            │
│  ├─ user: UserJpaEntity              @ManyToOne             │
│  ├─ orderDate: LocalDateTime         @Column                │
│  └─ totalAmount: BigDecimal          @Column                │
│                                                              │
│  OrderItemJpaEntity.java                                     │
│  @Entity @Table(name = "order_items")                        │
│  ├─ id: Long                                                 │
│  ├─ order: OrderJpaEntity            @ManyToOne             │
│  ├─ product: ProductJpaEntity        @ManyToOne             │
│  ├─ quantity: Integer                @Column                │
│  ├─ unitPrice: BigDecimal            @Column                │
│  └─ subtotal: BigDecimal             @Column                │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                   Database Layer                             │
│                                                              │
│  orders table:                                               │
│  ├─ id BIGINT PRIMARY KEY                                    │
│  ├─ client_id BIGINT → clients(id)                          │
│  ├─ state VARCHAR(50) DEFAULT 'PENDING'                     │
│  ├─ order_date TIMESTAMP                                     │
│  └─ total_amount DECIMAL(10,2)                              │
│                                                              │
│  order_items table:                                          │
│  ├─ id BIGINT PRIMARY KEY                                    │
│  ├─ order_id BIGINT → orders(id) ON DELETE CASCADE          │
│  ├─ product_id BIGINT → products(id)                        │
│  ├─ quantity INT                                             │
│  ├─ unit_price DECIMAL(10,2)                                │
│  └─ subtotal DECIMAL(10,2)                                  │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔄 Data Flow

### Creating an Order

```
1. Client Request:
   POST /api/orders?userId=1
   {
     "productIds": [1, 1, 1, 2, 2],
     "state": "PENDING"
   }
   
2. Controller:
   OrderInsert → OrderService.create()
   
3. Service:
   - Validate user exists
   - Count quantities: {1: 3, 2: 2}
   - For each product:
     * Fetch Product from repository
     * OrderItem.create(product, quantity)  ← Factory Method
     * Create OrderItemJpaEntity
   - Calculate total amount
   - Save to repository
   
4. Domain:
   OrderItem.create(product, 3):
     * Validate product not null
     * Validate quantity > 0
     * Set unitPrice = product.getFinalPrice()
     * Calculate subtotal = unitPrice × quantity
     * Return validated OrderItem
   
5. Repository:
   - Insert OrderJpaEntity
   - Cascade insert OrderItemJpaEntity (via @OneToMany)
   
6. Database:
   INSERT INTO orders (client_id, state, order_date, total_amount)
   VALUES (1, 'PENDING', NOW(), 155.17);
   
   INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal)
   VALUES (25, 1, 3, 41.39, 124.17);
   
   INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal)
   VALUES (25, 2, 2, 15.50, 31.00);
   
7. Response:
   OrderJpaEntity → Order → OrderDto → OrderResponse
   {
     "id": 25,
     "items": [
       {"id": 50, "product": {...}, "quantity": 3, ...},
       {"id": 51, "product": {...}, "quantity": 2, ...}
     ],
     "state": "PENDING",
     "totalAmount": 155.17
   }
```

---

## 🎯 Key Relationships

```
Order (1) ─────────────> (many) OrderItem (many) ─────────> (1) Product
   │                            │                                  │
   │                            │                                  │
   └──> User (1)                └──> unitPrice (frozen)           └──> finalPrice
        orderDate                    quantity
        totalAmount                  subtotal
        state
```

### Relationship Rules:
- **Order → OrderItem**: One-to-Many with cascade ALL
- **OrderItem → Product**: Many-to-One (product can be in multiple orders)
- **OrderItem → Order**: Many-to-One (bidirectional)
- **Order → User**: Many-to-One (user can have multiple orders)

---

## 🔒 Encapsulation & Protection

```
                    Order
    ┌────────────────────────────────┐
    │  - items: List<OrderItem>      │ ← Private field
    │                                │
    │  + getItems()                  │ → Returns unmodifiable list
    │    └─> Unmodifiable           │
    │                                │
    │  + addItem(item)               │ → Controlled modification
    │    ├─> Validate                │
    │    ├─> Merge if exists         │
    │    └─> Recalculate total       │
    │                                │
    │  + removeItem(item)            │ → Controlled removal
    │    └─> Recalculate total       │
    │                                │
    │  + setItems(newList)           │ → Full replacement
    │    ├─> Defensive copy          │
    │    └─> Recalculate total       │
    └────────────────────────────────┘
```

**Why?**
- Prevents external code from corrupting order state
- Ensures business rules are always enforced
- Maintains data integrity
- Automatic recalculation of derived values

---

## ✅ Validation Flow

```
    Client Input
         │
         ▼
    Controller Validation
    (@Valid, @NotNull, etc.)
         │
         ▼
    Service Layer
    (Business logic validation)
         │
         ▼
    Domain Model Validation  ← MOST IMPORTANT
    (OrderItem.create(), setters)
         │
         ├─> Product not null?
         ├─> Quantity > 0?
         ├─> Price >= 0?
         │
         ▼
    ✅ Valid Object Created
         │
         ▼
    Persistence Layer
    (JPA validation)
         │
         ▼
    Database Constraints
    (NOT NULL, FOREIGN KEY, etc.)
```

**Validation Layers:**
1. **Controller**: Basic format validation
2. **Service**: Business rule validation
3. **Domain**: Core object validation (MOST CRITICAL)
4. **JPA**: Entity validation
5. **Database**: Constraint validation

---

## 🚀 Request Flow Example

```
HTTP Request
    ↓
OrderController
    ├─> Deserialize JSON to OrderInsert
    ├─> Validate with @Valid
    ├─> Extract userId from @RequestParam
    └─> Call orderService.create(productIds, state, userId)
         ↓
OrderServiceImpl
    ├─> Validate user exists (UserRepository)
    ├─> Count product quantities (Stream.groupingBy)
    ├─> For each product:
    │    ├─> Fetch Product (ProductRepository)
    │    ├─> OrderItem.create(product, qty)  ← Factory
    │    │    ├─> Validate product not null
    │    │    ├─> Validate qty > 0
    │    │    ├─> Set unitPrice = product.finalPrice
    │    │    └─> Calculate subtotal
    │    └─> Create OrderItemJpaEntity
    ├─> Create OrderJpaEntity
    ├─> Calculate totalAmount
    └─> Save via OrderRepository
         ↓
OrderRepositoryImpl
    └─> Call orderJpaDao.insert(entity)
         ↓
OrderJpaDaoImpl
    ├─> entityManager.persist(entity)
    └─> Cascade to OrderItemJpaEntity (automatic)
         ↓
Database
    ├─> INSERT INTO orders
    └─> INSERT INTO order_items (×N)
         ↓
Response Path (reverse)
    ↓
OrderJpaEntity → Order → OrderDto → OrderResponse
    ↓
JSON Response to Client
```

---

## 🎨 Design Decisions

### Why Factory Method?
```java
// ❌ Constructor (complex, error-prone)
OrderItem item = new OrderItem(null, product, qty, product.getFinalPrice(), null);

// ✅ Factory Method (simple, safe)
OrderItem item = OrderItem.create(product, qty);
```
**Benefits:**
- Single point of creation
- Encapsulates complexity
- Enforces validation
- Consistent initialization

### Why Immutable Collections?
```java
// ❌ Direct modification (dangerous)
order.getItems().add(newItem); // Could bypass validation

// ✅ Controlled modification (safe)
order.addItem(newItem); // Validates and recalculates
```
**Benefits:**
- Prevents external corruption
- Enforces business rules
- Maintains invariants
- Predictable behavior

### Why Business Logic in Domain?
```java
// ❌ Service layer (fat service, anemic domain)
BigDecimal total = items.stream()
    .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
    .reduce(BigDecimal.ZERO, BigDecimal::add);

// ✅ Domain layer (rich domain, thin service)
BigDecimal total = order.calculateTotalAmount();
```
**Benefits:**
- Single Responsibility
- Reusability
- Testability
- Domain-Driven Design

---

## 📊 Complexity Comparison

### Before Refactoring:
- **Cyclomatic Complexity**: High (lots of if/else in service)
- **Lines of Code**: ~150 lines in service methods
- **Duplication**: High (same logic in create/update)
- **Testability**: Low (hard to test service logic)

### After Refactoring:
- **Cyclomatic Complexity**: Low (simple service, logic in domain)
- **Lines of Code**: ~80 lines in service (50% reduction)
- **Duplication**: None (reusable domain methods)
- **Testability**: High (easy to test domain methods)

---

**Version:** 2.0  
**Last Updated:** 2026-02-09  
**Status:** Production Ready ✅
