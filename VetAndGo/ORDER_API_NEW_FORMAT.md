# Order API - New JSON Format (v3.0)

## 🎯 Improved Request Format

We've upgraded the API to use a more scalable and intuitive format with explicit quantities.

---

## ✨ What Changed?

### ❌ OLD Format (Less Scalable):
```json
{
  "productIds": [1, 1, 1, 1, 1, 2, 2, 2],
  "state": "PENDING"
}
```
**Problems:**
- Hard to read (count duplicates manually)
- Inefficient for large quantities
- Not obvious what the quantity is
- Difficult to validate

### ✅ NEW Format (Much Better):
```json
{
  "items": [
    {"productId": 1, "quantity": 5},
    {"productId": 2, "quantity": 3}
  ],
  "state": "PENDING"
}
```
**Benefits:**
- ✅ Explicit quantities (clear and readable)
- ✅ More efficient (no duplicate IDs)
- ✅ Better validation
- ✅ Scalable for any quantity
- ✅ Industry standard format

---

## 📝 Complete API Examples

### 1. Create Order

**Endpoint:**
```
POST /api/orders?userId={userId}
```

**Request Body:**
```json
{
  "items": [
    {"productId": 101, "quantity": 3},
    {"productId": 102, "quantity": 2},
    {"productId": 103, "quantity": 1}
  ],
  "state": "PENDING"
}
```

**Response (201 Created):**
```json
{
  "id": 25,
  "items": [
    {
      "id": 50,
      "product": {
        "id": 101,
        "name": "Dog Food Premium",
        "category": {"id": 1, "name": "Food"},
        "basePrice": 45.99,
        "discountPercentage": 10.00,
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
        "category": {"id": 2, "name": "Toys"},
        "basePrice": 15.50,
        "discountPercentage": 0.00,
        "finalPrice": 15.50
      },
      "quantity": 2,
      "unitPrice": 15.50,
      "subtotal": 31.00
    },
    {
      "id": 52,
      "product": {
        "id": 103,
        "name": "Bird Cage",
        "category": {"id": 3, "name": "Accessories"},
        "basePrice": 120.00,
        "discountPercentage": 15.00,
        "finalPrice": 102.00
      },
      "quantity": 1,
      "unitPrice": 102.00,
      "subtotal": 102.00
    }
  ],
  "state": "PENDING",
  "user": {
    "id": 1,
    "username": "john_doe",
    "role": "CUSTOMER"
  },
  "orderDate": "2026-02-09T09:35:23",
  "totalAmount": 257.17
}
```

### 2. Update Order

**Endpoint:**
```
PUT /api/orders/{id}
```

**Request Body:**
```json
{
  "id": 25,
  "items": [
    {"productId": 101, "quantity": 5},
    {"productId": 104, "quantity": 2}
  ],
  "state": "PROCESSED"
}
```

**Response (200 OK):**
```json
{
  "id": 25,
  "items": [
    {
      "id": 53,
      "product": {
        "id": 101,
        "name": "Dog Food Premium",
        "finalPrice": 41.39
      },
      "quantity": 5,
      "unitPrice": 41.39,
      "subtotal": 206.95
    },
    {
      "id": 54,
      "product": {
        "id": 104,
        "name": "Pet Shampoo",
        "finalPrice": 12.34
      },
      "quantity": 2,
      "unitPrice": 12.34,
      "subtotal": 24.68
    }
  ],
  "state": "PROCESSED",
  "user": {
    "id": 1,
    "username": "john_doe",
    "role": "CUSTOMER"
  },
  "orderDate": "2026-02-09T09:35:23",
  "totalAmount": 231.63
}
```

---

## 🎯 Validation Rules

### OrderItemRequest Validation:
```json
{
  "productId": 101,    // ✅ Required, must not be null
  "quantity": 5        // ✅ Required, must be >= 1
}
```

**Valid Examples:**
```json
{"productId": 1, "quantity": 1}      // ✅ Minimum quantity
{"productId": 1, "quantity": 100}    // ✅ Large quantity
{"productId": 1, "quantity": 5}      // ✅ Normal quantity
```

**Invalid Examples:**
```json
{"productId": null, "quantity": 5}   // ❌ Product ID is null
{"productId": 1, "quantity": null}   // ❌ Quantity is null
{"productId": 1, "quantity": 0}      // ❌ Quantity must be >= 1
{"productId": 1, "quantity": -5}     // ❌ Quantity must be >= 1
{"quantity": 5}                      // ❌ Missing product ID
{"productId": 1}                     // ❌ Missing quantity
```

### OrderInsert Validation:
```json
{
  "items": [                           // ✅ Required, must have at least 1 item
    {"productId": 1, "quantity": 2}
  ],
  "state": "PENDING"                   // ✅ Required
}
```

**Error Response (400 Bad Request):**
```json
{
  "timestamp": "2026-02-09T09:35:23",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    {
      "field": "items[0].quantity",
      "message": "Quantity must be at least 1"
    }
  ]
}
```

---

## 📊 Comparison Examples

### Example 1: Small Order

**OLD Format:**
```json
{
  "productIds": [1, 1, 2],
  "state": "PENDING"
}
```

**NEW Format:**
```json
{
  "items": [
    {"productId": 1, "quantity": 2},
    {"productId": 2, "quantity": 1}
  ],
  "state": "PENDING"
}
```

### Example 2: Large Quantities

**OLD Format (Very Hard to Read!):**
```json
{
  "productIds": [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
  "state": "PENDING"
}
```

**NEW Format (Clear and Concise!):**
```json
{
  "items": [
    {"productId": 1, "quantity": 20},
    {"productId": 2, "quantity": 10}
  ],
  "state": "PENDING"
}
```

### Example 3: Many Different Products

**OLD Format:**
```json
{
  "productIds": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
  "state": "PENDING"
}
```

**NEW Format (Same, but with explicit quantities):**
```json
{
  "items": [
    {"productId": 1, "quantity": 1},
    {"productId": 2, "quantity": 1},
    {"productId": 3, "quantity": 1},
    {"productId": 4, "quantity": 1},
    {"productId": 5, "quantity": 1},
    {"productId": 6, "quantity": 1},
    {"productId": 7, "quantity": 1},
    {"productId": 8, "quantity": 1},
    {"productId": 9, "quantity": 1},
    {"productId": 10, "quantity": 1}
  ],
  "state": "PENDING"
}
```

---

## 🚀 cURL Examples

### Create Order
```bash
curl -X POST "http://localhost:8080/api/orders?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "items": [
      {"productId": 1, "quantity": 5},
      {"productId": 2, "quantity": 3}
    ],
    "state": "PENDING"
  }'
```

### Update Order
```bash
curl -X PUT "http://localhost:8080/api/orders/25" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 25,
    "items": [
      {"productId": 1, "quantity": 10},
      {"productId": 3, "quantity": 2}
    ],
    "state": "PROCESSED"
  }'
```

### Minimal Update (Only State)
```bash
curl -X PUT "http://localhost:8080/api/orders/25" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 25,
    "state": "DELIVERED"
  }'
```

---

## 💡 Special Cases

### Duplicate Product IDs (Merged Automatically)
**Request:**
```json
{
  "items": [
    {"productId": 1, "quantity": 3},
    {"productId": 1, "quantity": 2},
    {"productId": 1, "quantity": 5}
  ],
  "state": "PENDING"
}
```

**Processing:**
The system automatically merges duplicates:
- Product 1: 3 + 2 + 5 = 10 units

**Response:**
```json
{
  "items": [
    {
      "product": {"id": 1, "name": "Dog Food"},
      "quantity": 10,
      "unitPrice": 41.39,
      "subtotal": 413.90
    }
  ],
  "totalAmount": 413.90
}
```

### Empty Items (Error)
**Request:**
```json
{
  "items": [],
  "state": "PENDING"
}
```

**Response (400 Bad Request):**
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Order must have at least one item"
}
```

---

## 📋 Request/Response Model Reference

### OrderItemRequest (Input)
```typescript
{
  productId: number;    // Required, must not be null
  quantity: number;     // Required, must be >= 1
}
```

### OrderInsert (Input)
```typescript
{
  items: OrderItemRequest[];  // Required, must have at least 1 item
  state: string;              // Required: "PENDING" | "PROCESSED" | "DELIVERED"
}
```

### OrderUpdate (Input)
```typescript
{
  id: number;                 // Required
  items?: OrderItemRequest[]; // Optional (null = don't update items)
  state?: string;             // Optional (null = don't update state)
}
```

### OrderItemResponse (Output)
```typescript
{
  id: number;
  product: ProductResponse;
  quantity: number;
  unitPrice: number;
  subtotal: number;
}
```

### OrderResponse (Output)
```typescript
{
  id: number;
  items: OrderItemResponse[];
  state: string;
  user: UserDetail;
  orderDate: string;          // ISO 8601 format
  totalAmount: number;
}
```

---

## 🎯 Benefits Summary

| Feature | OLD Format | NEW Format |
|---------|-----------|-----------|
| **Readability** | ❌ Count duplicates | ✅ Explicit numbers |
| **Efficiency** | ❌ Repeated IDs | ✅ Single entry per product |
| **Validation** | ⚠️ Client-side only | ✅ Server validation |
| **Scalability** | ❌ Large arrays | ✅ Compact format |
| **Clarity** | ❌ Implicit quantities | ✅ Explicit quantities |
| **Industry Standard** | ❌ Custom format | ✅ Standard e-commerce |

---

## 🔄 Migration Guide

If you're using the old format, update your client code:

### JavaScript/TypeScript:
```typescript
// OLD
const order = {
  productIds: [1, 1, 1, 2, 2],
  state: "PENDING"
};

// NEW
const order = {
  items: [
    { productId: 1, quantity: 3 },
    { productId: 2, quantity: 2 }
  ],
  state: "PENDING"
};
```

### Java:
```java
// OLD
List<Long> productIds = Arrays.asList(1L, 1L, 1L, 2L, 2L);
OrderInsert order = new OrderInsert(productIds, OrderState.PENDING);

// NEW
List<OrderItemRequest> items = Arrays.asList(
    new OrderItemRequest(1L, 3),
    new OrderItemRequest(2L, 2)
);
OrderInsert order = new OrderInsert(items, OrderState.PENDING);
```

### Python:
```python
# OLD
order = {
    "productIds": [1, 1, 1, 2, 2],
    "state": "PENDING"
}

# NEW
order = {
    "items": [
        {"productId": 1, "quantity": 3},
        {"productId": 2, "quantity": 2}
    ],
    "state": "PENDING"
}
```

---

**Version:** 3.0 (New JSON Format)  
**Updated:** 2026-02-09  
**Status:** Production Ready ✅
