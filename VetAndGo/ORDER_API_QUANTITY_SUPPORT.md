# Order API - Quantity Support

## Important Change: Order Items with Quantities

The Order API now supports **quantities** for products. Instead of listing the same product ID multiple times, the system automatically groups duplicate product IDs and tracks quantities.

---

## How It Works

### Input Format (Request)
You send a **list of product IDs**, and duplicates are counted as quantities:

```json
{
  "productIds": [1, 1, 1, 2, 2, 3],
  "state": "PENDING"
}
```

### Output Format (Response)
The order returns **items** with quantity information:

```json
{
  "id": 15,
  "items": [
    {
      "id": 1,
      "product": {
        "id": 1,
        "name": "Dog Food Premium",
        "finalPrice": 41.39,
        ...
      },
      "quantity": 3,
      "unitPrice": 41.39,
      "subtotal": 124.17
    },
    {
      "id": 2,
      "product": {
        "id": 2,
        "name": "Cat Toy",
        "finalPrice": 15.50,
        ...
      },
      "quantity": 2,
      "unitPrice": 15.50,
      "subtotal": 31.00
    },
    {
      "id": 3,
      "product": {
        "id": 3,
        "name": "Bird Cage",
        "finalPrice": 102.00,
        ...
      },
      "quantity": 1,
      "unitPrice": 102.00,
      "subtotal": 102.00
    }
  ],
  "state": "PENDING",
  "user": { ... },
  "orderDate": "2026-02-09T09:00:00",
  "totalAmount": 257.17
}
```

---

## Key Features

### 1. Automatic Quantity Counting
**Input:**
```json
{
  "productIds": [1, 1, 1, 1, 1, 1, 1]
}
```

**Result:**
- Product ID 1 with quantity 7
- Total amount = unit price × 7

### 2. Mixed Products
**Input:**
```json
{
  "productIds": [1, 2, 1, 3, 1, 2]
}
```

**Result:**
- Product ID 1: quantity 3
- Product ID 2: quantity 2
- Product ID 3: quantity 1

### 3. Price Calculation
Each order item stores:
- **unitPrice**: The product's final price at the time of order
- **quantity**: Number of units ordered
- **subtotal**: unitPrice × quantity

The order's **totalAmount** is the sum of all item subtotals.

---

## Complete Example

### Create Order Request
```bash
curl -X POST "http://localhost:8080/api/orders?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "productIds": [101, 101, 101, 102, 102],
    "state": "PENDING"
  }'
```

### Response
```json
{
  "id": 25,
  "items": [
    {
      "id": 50,
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
      "quantity": 3,
      "unitPrice": 41.39,
      "subtotal": 124.17
    },
    {
      "id": 51,
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
      "quantity": 2,
      "unitPrice": 15.50,
      "subtotal": 31.00
    }
  ],
  "state": "PENDING",
  "user": {
    "id": 1,
    "username": "john_doe",
    "role": "CUSTOMER"
  },
  "orderDate": "2026-02-09T09:05:23",
  "totalAmount": 155.17
}
```

---

## Database Schema

### orders table
```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    state VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);
```

### order_items table (NEW)
```sql
CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

---

## Benefits

✅ **Handles duplicate products correctly** - No more 500 errors
✅ **Accurate quantity tracking** - Know exactly how many of each product
✅ **Price history** - Unit price is stored at order time
✅ **Subtotal calculations** - Automatic per-item and total calculations
✅ **Better data model** - Follows e-commerce best practices

---

## Migration Notes

If you have existing data in the old `order_products` table, you'll need to:

1. Drop the old junction table
2. Create the new `order_items` table
3. Migrate existing data (assuming quantity 1 for all old records)

The migration SQL is already included in `V1__create_tables.sql`.

---

**Updated:** 2026-02-09
**Version:** 2.0 (with quantity support)
