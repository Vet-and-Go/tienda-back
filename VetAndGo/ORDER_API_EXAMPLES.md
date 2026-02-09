# Order API - JSON Request/Response Examples

This document provides complete JSON examples for all Order Controller endpoints.

---

## Base URL
```
http://localhost:8080/api/orders
```

---

## 1. GET All Orders

### Endpoint
```
GET /api/orders
GET /api/orders?userId={userId}
```

### Request
**Without filter:**
```http
GET /api/orders
```

**With userId filter:**
```http
GET /api/orders?userId=1
```

### Response (200 OK)
```json
[
  {
    "id": 1,
    "products": [
      {
        "id": 101,
        "name": "Dog Food Premium",
        "category": {
          "id": 1,
          "name": "Food",
          "description": "Pet food products"
        },
        "description": "High quality dog food",
        "basePrice": 45.99,
        "stock": 100,
        "discountPercentage": 10.00,
        "finalPrice": 41.39,
        "imageUrl": "https://example.com/dog-food.jpg"
      },
      {
        "id": 102,
        "name": "Cat Toy",
        "category": {
          "id": 2,
          "name": "Toys",
          "description": "Pet toys and accessories"
        },
        "description": "Interactive cat toy",
        "basePrice": 15.50,
        "stock": 50,
        "discountPercentage": 0.00,
        "finalPrice": 15.50,
        "imageUrl": "https://example.com/cat-toy.jpg"
      }
    ],
    "state": "PENDING",
    "user": {
      "id": 1,
      "username": "john_doe",
      "role": "CUSTOMER"
    },
    "orderDate": "2026-02-09T08:30:15",
    "totalAmount": 56.89
  },
  {
    "id": 2,
    "products": [
      {
        "id": 103,
        "name": "Bird Cage",
        "category": {
          "id": 3,
          "name": "Accessories",
          "description": "Pet accessories"
        },
        "description": "Large bird cage",
        "basePrice": 120.00,
        "stock": 20,
        "discountPercentage": 15.00,
        "finalPrice": 102.00,
        "imageUrl": "https://example.com/bird-cage.jpg"
      }
    ],
    "state": "PROCESSED",
    "user": {
      "id": 1,
      "username": "john_doe",
      "role": "CUSTOMER"
    },
    "orderDate": "2026-02-08T14:20:00",
    "totalAmount": 102.00
  }
]
```

### Empty Response (when no orders found)
```json
[]
```

---

## 2. GET Order by ID

### Endpoint
```
GET /api/orders/{id}
```

### Request
```http
GET /api/orders/1
```

### Response (200 OK)
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "product": {
        "id": 101,
        "name": "Dog Food Premium",
        "category": {
          "id": 1,
          "name": "Food",
          "description": "Pet food products"
        },
        "description": "High quality dog food",
        "basePrice": 45.99,
        "stock": 100,
        "discountPercentage": 10.00,
        "finalPrice": 41.39,
        "imageUrl": "https://example.com/dog-food.jpg"
      },
      "quantity": 2,
      "unitPrice": 41.39,
      "subtotal": 82.78
    },
    {
      "id": 2,
      "product": {
        "id": 102,
        "name": "Cat Toy",
        "category": {
          "id": 2,
          "name": "Toys",
          "description": "Pet toys and accessories"
        },
        "description": "Interactive cat toy",
        "basePrice": 15.50,
        "stock": 50,
        "discountPercentage": 0.00,
        "finalPrice": 15.50,
        "imageUrl": "https://example.com/cat-toy.jpg"
      },
      "quantity": 1,
      "unitPrice": 15.50,
      "subtotal": 15.50
    }
  ],
  "state": "PENDING",
  "user": {
    "id": 1,
    "username": "john_doe",
    "role": "CUSTOMER"
  },
  "orderDate": "2026-02-09T08:30:15",
  "totalAmount": 98.28
}
```

### Error Response (404 Not Found)
```json
{
  "timestamp": "2026-02-09T08:45:30",
  "status": 404,
  "error": "Not Found",
  "message": "Order not found",
  "path": "/api/orders/999"
}
```

---

## 3. CREATE New Order

### Endpoint
```
POST /api/orders?userId={userId}
```

### Request
```http
POST /api/orders?userId=1
Content-Type: application/json
```

### Request Body
```json
{
  "productIds": [101, 102, 105],
  "state": "PENDING"
}
```

**Minimal Request (state is optional, defaults to PENDING):**
```json
{
  "productIds": [101, 102]
}
```

**With multiple quantities of same product (duplicates are counted):**
```json
{
  "productIds": [101, 101, 101, 102, 102],
  "state": "PENDING"
}
```
_This creates an order with 3 units of product 101 and 2 units of product 102_

**With explicit state:**
```json
{
  "productIds": [101, 102, 105],
  "state": "PROCESSED"
}
```

### Response (201 CREATED)
```json
{
  "id": 15,
  "products": [
    {
      "id": 101,
      "name": "Dog Food Premium",
      "category": {
        "id": 1,
        "name": "Food",
        "description": "Pet food products"
      },
      "description": "High quality dog food",
      "basePrice": 45.99,
      "stock": 100,
      "discountPercentage": 10.00,
      "finalPrice": 41.39,
      "imageUrl": "https://example.com/dog-food.jpg"
    },
    {
      "id": 102,
      "name": "Cat Toy",
      "category": {
        "id": 2,
        "name": "Toys",
        "description": "Pet toys and accessories"
      },
      "description": "Interactive cat toy",
      "basePrice": 15.50,
      "stock": 50,
      "discountPercentage": 0.00,
      "finalPrice": 15.50,
      "imageUrl": "https://example.com/cat-toy.jpg"
    },
    {
      "id": 105,
      "name": "Pet Shampoo",
      "category": {
        "id": 4,
        "name": "Grooming",
        "description": "Pet grooming products"
      },
      "description": "Gentle pet shampoo",
      "basePrice": 12.99,
      "stock": 75,
      "discountPercentage": 5.00,
      "finalPrice": 12.34,
      "imageUrl": "https://example.com/shampoo.jpg"
    }
  ],
  "state": "PENDING",
  "user": {
    "id": 1,
    "username": "john_doe",
    "role": "CUSTOMER"
  },
  "orderDate": "2026-02-09T08:53:14",
  "totalAmount": 69.23
}
```

### Validation Error Response (400 Bad Request)
```json
{
  "timestamp": "2026-02-09T08:53:14",
  "status": 400,
  "error": "Bad Request",
  "message": "Product IDs must not be empty",
  "path": "/api/orders"
}
```

### Error Response - Product Not Found (404)
```json
{
  "timestamp": "2026-02-09T08:53:14",
  "status": 404,
  "error": "Not Found",
  "message": "Product with ID 999 not found",
  "path": "/api/orders"
}
```

### Error Response - User Not Found (404)
```json
{
  "timestamp": "2026-02-09T08:53:14",
  "status": 404,
  "error": "Not Found",
  "message": "User not found",
  "path": "/api/orders"
}
```

---

## 4. UPDATE Order

### Endpoint
```
PUT /api/orders/{id}
```

### Request
```http
PUT /api/orders/1
Content-Type: application/json
```

### Request Body Options

**Update both products and state:**
```json
{
  "id": 1,
  "productIds": [101, 103],
  "state": "PROCESSED"
}
```

**Update only products (state remains unchanged):**
```json
{
  "id": 1,
  "productIds": [101, 102, 105]
}
```

**Update only state (products remain unchanged):**
```json
{
  "id": 1,
  "state": "DELIVERED"
}
```

**No changes (both null, nothing updates):**
```json
{
  "id": 1
}
```

### Response (200 OK)
```json
{
  "id": 1,
  "products": [
    {
      "id": 101,
      "name": "Dog Food Premium",
      "category": {
        "id": 1,
        "name": "Food",
        "description": "Pet food products"
      },
      "description": "High quality dog food",
      "basePrice": 45.99,
      "stock": 100,
      "discountPercentage": 10.00,
      "finalPrice": 41.39,
      "imageUrl": "https://example.com/dog-food.jpg"
    },
    {
      "id": 103,
      "name": "Bird Cage",
      "category": {
        "id": 3,
        "name": "Accessories",
        "description": "Pet accessories"
      },
      "description": "Large bird cage",
      "basePrice": 120.00,
      "stock": 20,
      "discountPercentage": 15.00,
      "finalPrice": 102.00,
      "imageUrl": "https://example.com/bird-cage.jpg"
    }
  ],
  "state": "PROCESSED",
  "user": {
    "id": 1,
    "username": "john_doe",
    "role": "CUSTOMER"
  },
  "orderDate": "2026-02-09T08:30:15",
  "totalAmount": 143.39
}
```

### Error Response (404 Not Found)
```json
{
  "timestamp": "2026-02-09T08:53:14",
  "status": 404,
  "error": "Not Found",
  "message": "Order with ID 999 not found.",
  "path": "/api/orders/999"
}
```

### Validation Error Response (400 Bad Request)
```json
{
  "timestamp": "2026-02-09T08:53:14",
  "status": 400,
  "error": "Bad Request",
  "message": "Order ID must not be null",
  "path": "/api/orders/1"
}
```

---

## 5. CHANGE Order State

### Endpoint
```
PATCH /api/orders/{id}/state/{state}
```

### Request Examples
```http
PATCH /api/orders/1/state/PENDING
```

```http
PATCH /api/orders/1/state/PROCESSED
```

```http
PATCH /api/orders/1/state/DELIVERED
```

### Valid State Values
- `PENDING`
- `PROCESSED`
- `DELIVERED`

### Response (200 OK)
```json
{
  "id": 1,
  "products": [
    {
      "id": 101,
      "name": "Dog Food Premium",
      "category": {
        "id": 1,
        "name": "Food",
        "description": "Pet food products"
      },
      "description": "High quality dog food",
      "basePrice": 45.99,
      "stock": 100,
      "discountPercentage": 10.00,
      "finalPrice": 41.39,
      "imageUrl": "https://example.com/dog-food.jpg"
    },
    {
      "id": 102,
      "name": "Cat Toy",
      "category": {
        "id": 2,
        "name": "Toys",
        "description": "Pet toys and accessories"
      },
      "description": "Interactive cat toy",
      "basePrice": 15.50,
      "stock": 50,
      "discountPercentage": 0.00,
      "finalPrice": 15.50,
      "imageUrl": "https://example.com/cat-toy.jpg"
    }
  ],
  "state": "DELIVERED",
  "user": {
    "id": 1,
    "username": "john_doe",
    "role": "CUSTOMER"
  },
  "orderDate": "2026-02-09T08:30:15",
  "totalAmount": 56.89
}
```

### Error Response - Invalid State (400 Bad Request)
```json
{
  "timestamp": "2026-02-09T08:53:14",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid state value: INVALID_STATE",
  "path": "/api/orders/1/state/INVALID_STATE"
}
```

### Error Response - Order Not Found (404)
```json
{
  "timestamp": "2026-02-09T08:53:14",
  "status": 404,
  "error": "Not Found",
  "message": "Order with ID 999 not found.",
  "path": "/api/orders/999/state/PROCESSED"
}
```

---

## 6. DELETE Order

### Endpoint
```
DELETE /api/orders/{id}
```

### Request
```http
DELETE /api/orders/1
```

### Response (204 No Content)
```
(Empty body - successful deletion)
```

### Error Response (404 Not Found)
```json
{
  "timestamp": "2026-02-09T08:53:14",
  "status": 404,
  "error": "Not Found",
  "message": "Order not found",
  "path": "/api/orders/999"
}
```

---

## Complete cURL Examples

### 1. Get All Orders
```bash
curl -X GET http://localhost:8080/api/orders
```

### 2. Get Orders for Specific User
```bash
curl -X GET "http://localhost:8080/api/orders?userId=1"
```

### 3. Get Order by ID
```bash
curl -X GET http://localhost:8080/api/orders/1
```

### 4. Create New Order
```bash
curl -X POST "http://localhost:8080/api/orders?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "productIds": [101, 102, 105],
    "state": "PENDING"
  }'
```

### 5. Update Order
```bash
curl -X PUT http://localhost:8080/api/orders/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "productIds": [101, 103],
    "state": "PROCESSED"
  }'
```

### 6. Change Order State
```bash
curl -X PATCH http://localhost:8080/api/orders/1/state/DELIVERED
```

### 7. Delete Order
```bash
curl -X DELETE http://localhost:8080/api/orders/1
```

---

## Postman Collection Format

### Environment Variables
```json
{
  "base_url": "http://localhost:8080",
  "user_id": "1",
  "order_id": "1",
  "product_id_1": "101",
  "product_id_2": "102"
}
```

### Headers (for all requests)
```json
{
  "Content-Type": "application/json",
  "Accept": "application/json"
}
```

---

## Notes

1. **orderDate** is automatically set by the server when creating an order (you cannot specify it in the request)
2. **totalAmount** is automatically calculated from product prices (you cannot specify it in the request)
3. **state** defaults to `PENDING` if not specified during creation
4. **productIds** must reference existing products in the database
5. **userId** must reference an existing user in the database
6. All monetary values use 2 decimal places
7. Dates are in ISO 8601 format without timezone (LocalDateTime)

---

## HTTP Status Codes Used

- **200 OK** - Successful GET, PUT, PATCH
- **201 CREATED** - Successful POST (order created)
- **204 NO CONTENT** - Successful DELETE
- **400 BAD REQUEST** - Validation errors, invalid input
- **404 NOT FOUND** - Resource not found (order, product, or user)
- **500 INTERNAL SERVER ERROR** - Server-side errors

---

**Generated:** 2026-02-09  
**API Version:** 1.0  
**Spring Boot Version:** 3.5.7
