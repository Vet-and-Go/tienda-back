# Order API - Complete Examples (v3.0)

## 🎯 Quick Start

### New JSON Format
```json
{
  "items": [
    {"productId": 1, "quantity": 5},
    {"productId": 2, "quantity": 3}
  ],
  "state": "PENDING"
}
```

---

## 📝 Complete API Examples

### 1️⃣ Create Order - Simple

**Request:**
```bash
POST /api/orders?userId=1
Content-Type: application/json

{
  "items": [
    {"productId": 101, "quantity": 2}
  ],
  "state": "PENDING"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "product": {
        "id": 101,
        "name": "Dog Food Premium",
        "finalPrice": 41.39
      },
      "quantity": 2,
      "unitPrice": 41.39,
      "subtotal": 82.78
    }
  ],
  "state": "PENDING",
  "user": {"id": 1, "username": "john_doe"},
  "orderDate": "2026-02-09T09:35:00",
  "totalAmount": 82.78
}
```

---

### 2️⃣ Create Order - Multiple Items

**Request:**
```bash
POST /api/orders?userId=1
Content-Type: application/json

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
  "id": 2,
  "items": [
    {
      "id": 2,
      "product": {"id": 101, "name": "Dog Food Premium", "finalPrice": 41.39},
      "quantity": 3,
      "unitPrice": 41.39,
      "subtotal": 124.17
    },
    {
      "id": 3,
      "product": {"id": 102, "name": "Cat Toy", "finalPrice": 15.50},
      "quantity": 2,
      "unitPrice": 15.50,
      "subtotal": 31.00
    },
    {
      "id": 4,
      "product": {"id": 103, "name": "Bird Cage", "finalPrice": 102.00},
      "quantity": 1,
      "unitPrice": 102.00,
      "subtotal": 102.00
    }
  ],
  "state": "PENDING",
  "user": {"id": 1, "username": "john_doe"},
  "orderDate": "2026-02-09T09:36:00",
  "totalAmount": 257.17
}
```

---

### 3️⃣ Create Order - Large Quantities

**Request:**
```bash
POST /api/orders?userId=1
Content-Type: application/json

{
  "items": [
    {"productId": 101, "quantity": 50},
    {"productId": 102, "quantity": 25}
  ],
  "state": "PENDING"
}
```

**Response (201 Created):**
```json
{
  "id": 3,
  "items": [
    {
      "id": 5,
      "product": {"id": 101, "name": "Dog Food Premium", "finalPrice": 41.39},
      "quantity": 50,
      "unitPrice": 41.39,
      "subtotal": 2069.50
    },
    {
      "id": 6,
      "product": {"id": 102, "name": "Cat Toy", "finalPrice": 15.50},
      "quantity": 25,
      "unitPrice": 15.50,
      "subtotal": 387.50
    }
  ],
  "state": "PENDING",
  "user": {"id": 1, "username": "john_doe"},
  "orderDate": "2026-02-09T09:37:00",
  "totalAmount": 2457.00
}
```

---

### 4️⃣ Create Order - Duplicate Product IDs (Auto-Merged)

**Request:**
```bash
POST /api/orders?userId=1
Content-Type: application/json

{
  "items": [
    {"productId": 101, "quantity": 3},
    {"productId": 101, "quantity": 2},
    {"productId": 101, "quantity": 5}
  ],
  "state": "PENDING"
}
```

**Processing:**
System automatically merges: 3 + 2 + 5 = 10 units

**Response (201 Created):**
```json
{
  "id": 4,
  "items": [
    {
      "id": 7,
      "product": {"id": 101, "name": "Dog Food Premium", "finalPrice": 41.39},
      "quantity": 10,
      "unitPrice": 41.39,
      "subtotal": 413.90
    }
  ],
  "state": "PENDING",
  "user": {"id": 1, "username": "john_doe"},
  "orderDate": "2026-02-09T09:38:00",
  "totalAmount": 413.90
}
```

---

### 5️⃣ Get All Orders

**Request:**
```bash
GET /api/orders
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "items": [...],
    "state": "PENDING",
    "user": {"id": 1, "username": "john_doe"},
    "orderDate": "2026-02-09T09:35:00",
    "totalAmount": 82.78
  },
  {
    "id": 2,
    "items": [...],
    "state": "PROCESSED",
    "user": {"id": 1, "username": "john_doe"},
    "orderDate": "2026-02-09T09:36:00",
    "totalAmount": 257.17
  }
]
```

---

### 6️⃣ Get Orders by User

**Request:**
```bash
GET /api/orders?userId=1
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "items": [...],
    "state": "PENDING",
    "user": {"id": 1, "username": "john_doe"},
    "orderDate": "2026-02-09T09:35:00",
    "totalAmount": 82.78
  }
]
```

---

### 7️⃣ Get Order by ID

**Request:**
```bash
GET /api/orders/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "product": {
        "id": 101,
        "name": "Dog Food Premium",
        "category": {"id": 1, "name": "Food"},
        "finalPrice": 41.39
      },
      "quantity": 2,
      "unitPrice": 41.39,
      "subtotal": 82.78
    }
  ],
  "state": "PENDING",
  "user": {"id": 1, "username": "john_doe"},
  "orderDate": "2026-02-09T09:35:00",
  "totalAmount": 82.78
}
```

---

### 8️⃣ Update Order - Change Items

**Request:**
```bash
PUT /api/orders/1
Content-Type: application/json

{
  "id": 1,
  "items": [
    {"productId": 101, "quantity": 5},
    {"productId": 102, "quantity": 3}
  ],
  "state": "PROCESSED"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "items": [
    {
      "id": 8,
      "product": {"id": 101, "name": "Dog Food Premium", "finalPrice": 41.39},
      "quantity": 5,
      "unitPrice": 41.39,
      "subtotal": 206.95
    },
    {
      "id": 9,
      "product": {"id": 102, "name": "Cat Toy", "finalPrice": 15.50},
      "quantity": 3,
      "unitPrice": 15.50,
      "subtotal": 46.50
    }
  ],
  "state": "PROCESSED",
  "user": {"id": 1, "username": "john_doe"},
  "orderDate": "2026-02-09T09:35:00",
  "totalAmount": 253.45
}
```

---

### 9️⃣ Update Order - Only State

**Request:**
```bash
PUT /api/orders/1
Content-Type: application/json

{
  "id": 1,
  "state": "DELIVERED"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "items": [...],  // Items unchanged
  "state": "DELIVERED",  // Only state changed
  "user": {"id": 1, "username": "john_doe"},
  "orderDate": "2026-02-09T09:35:00",
  "totalAmount": 253.45
}
```

---

### 🔟 Change Order State

**Request:**
```bash
PATCH /api/orders/1/state/DELIVERED
```

**Response (200 OK):**
```json
{
  "id": 1,
  "items": [...],
  "state": "DELIVERED",
  "user": {"id": 1, "username": "john_doe"},
  "orderDate": "2026-02-09T09:35:00",
  "totalAmount": 253.45
}
```

---

### 1️⃣1️⃣ Delete Order

**Request:**
```bash
DELETE /api/orders/1
```

**Response (204 No Content):**
```
(Empty body)
```

---

## ❌ Error Examples

### Error 1: Missing Required Field

**Request:**
```json
{
  "items": [
    {"productId": 101}
  ],
  "state": "PENDING"
}
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2026-02-09T09:40:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    {
      "field": "items[0].quantity",
      "message": "Quantity must not be null"
    }
  ]
}
```

---

### Error 2: Invalid Quantity

**Request:**
```json
{
  "items": [
    {"productId": 101, "quantity": 0}
  ],
  "state": "PENDING"
}
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2026-02-09T09:40:00",
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

### Error 3: Empty Items

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
  "timestamp": "2026-02-09T09:40:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Order must have at least one item"
}
```

---

### Error 4: Product Not Found

**Request:**
```json
{
  "items": [
    {"productId": 999, "quantity": 1}
  ],
  "state": "PENDING"
}
```

**Response (404 Not Found):**
```json
{
  "timestamp": "2026-02-09T09:40:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product with ID 999 not found"
}
```

---

### Error 5: User Not Found

**Request:**
```bash
POST /api/orders?userId=999
```

**Response (404 Not Found):**
```json
{
  "timestamp": "2026-02-09T09:40:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found"
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

### Get All Orders
```bash
curl -X GET "http://localhost:8080/api/orders"
```

### Get Order by ID
```bash
curl -X GET "http://localhost:8080/api/orders/1"
```

### Update Order
```bash
curl -X PUT "http://localhost:8080/api/orders/1" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "items": [
      {"productId": 1, "quantity": 10}
    ],
    "state": "PROCESSED"
  }'
```

### Change State
```bash
curl -X PATCH "http://localhost:8080/api/orders/1/state/DELIVERED"
```

### Delete Order
```bash
curl -X DELETE "http://localhost:8080/api/orders/1"
```

---

**Version:** 3.0 (Final)  
**Updated:** 2026-02-09  
**Status:** Production Ready ✅
