# Sistema de Autenticación con Anotaciones

## 🎯 Descripción

Sistema flexible de autenticación basado en anotaciones que te permite controlar fácilmente qué endpoints requieren autenticación y qué rol necesitan.

## 📝 Anotaciones Disponibles

### 1. `@PublicEndpoint`
Marca un endpoint como **público** (sin autenticación requerida).

**Uso:**
```java
@PublicEndpoint
@PostMapping("/api/users")
public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
    // Código...
}
```

### 2. `@RequireAdmin`
Marca un endpoint como **protegido** y requiere que el usuario tenga rol **ADMIN**.

**Uso:**
```java
@RequireAdmin
@GetMapping("/api/users")
public ResponseEntity<List<UserDto>> getAllUsers() {
    // Código...
}
```

### 3. Sin anotación
Si un endpoint **no tiene ninguna anotación**, por defecto requiere **autenticación básica** (cualquier usuario autenticado puede acceder, sin importar el rol).

**Uso:**
```java
@GetMapping("/api/profile")
public ResponseEntity<UserDto> getMyProfile() {
    // Cualquier usuario autenticado puede acceder
}
```

## 📋 Ejemplos

### Ejemplo 1: Endpoint completamente público
```java
@PublicEndpoint
@GetMapping("/api/products")
public ResponseEntity<List<ProductDto>> getAllProducts() {
    // Accesible sin autenticación
}
```

### Ejemplo 2: Endpoint solo para ADMIN
```java
@RequireAdmin
@DeleteMapping("/api/users/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    // Solo usuarios con rol ADMIN
}
```

### Ejemplo 3: Endpoint para cualquier usuario autenticado
```java
// Sin anotación
@GetMapping("/api/my-orders")
public ResponseEntity<List<OrderDto>> getMyOrders() {
    // Requiere token válido, cualquier rol
}
```

### Ejemplo 4: Login (público)
```java
@PublicEndpoint
@PostMapping("/api/auth/login")
public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
    // Login no requiere autenticación previa
}
```

## 🔧 Configuración en tu Controlador

Simplemente agrega las anotaciones sobre los métodos:

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Público - Ver productos
    @PublicEndpoint
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() { }

    // Autenticado - Crear producto (cualquier usuario)
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto dto) { }

    // Solo ADMIN - Eliminar producto
    @RequireAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) { }
}
```

## 🚀 Ventajas

1. **Simple**: Solo agrega una anotación
2. **Flexible**: Cada endpoint puede tener su propia configuración
3. **Legible**: Se ve claramente qué endpoints están protegidos
4. **Mantenible**: No necesitas modificar el filtro cada vez que agregues endpoints

## 🔐 Códigos de Respuesta

- `200 OK` - Petición exitosa
- `201 Created` - Recurso creado
- `401 Unauthorized` - Token inválido o faltante
- `403 Forbidden` - Usuario no tiene rol ADMIN
- `404 Not Found` - Recurso no encontrado
- `500 Internal Server Error` - Error del servidor

## 📊 Matriz de Acceso

| Anotación | Sin Token | Token USER | Token ADMIN |
|-----------|-----------|------------|-------------|
| `@PublicEndpoint` | ✅ | ✅ | ✅ |
| Sin anotación | ❌ | ✅ | ✅ |
| `@RequireAdmin` | ❌ | ❌ | ✅ |

## 🎓 Guía Rápida

1. **¿Endpoint público (ej: login, registro)?** → `@PublicEndpoint`
2. **¿Solo para ADMIN?** → `@RequireAdmin`
3. **¿Cualquier usuario autenticado?** → Sin anotación

## 📝 Nota Importante

- El filtro `AuthFilter` automáticamente detecta estas anotaciones
- No necesitas modificar código adicional
- Las anotaciones se evalúan en tiempo de ejecución

