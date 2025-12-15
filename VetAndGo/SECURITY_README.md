# Sistema de Autenticación y Autorización

## Descripción

Se ha implementado un sistema de autenticación basado en tokens para proteger los endpoints de la API.

## Funcionamiento

### 1. Rutas Públicas (No requieren autenticación ni token)
- `POST /api/auth/login` - Login de usuarios
- `POST /api/users` - Registro de nuevos usuarios (crear cuenta)

### 2. Rutas Protegidas (Requieren token de ADMIN)
- `GET /api/users` - Listar todos los usuarios
- `GET /api/users/{id}` - Obtener usuario por ID
- `GET /api/users/username/{username}` - Obtener usuario por username
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

## Cómo usar

### 1. Crear un usuario ADMIN
```bash
POST http://localhost:8080/api/users
Content-Type: application/json

{
    "username": "admin",
    "password": "1234",
    "role": "ADMIN"
}
```

### 2. Login para obtener el token
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "1234"
}
```

**Respuesta:**
```json
{
    "token": "123e4567-e89b-12d3-a456-426614174000",
    "username": "admin",
    "role": "ADMIN"
}
```

### 3. Usar el token en peticiones protegidas
```bash
GET http://localhost:8080/api/users
Authorization: Bearer 123e4567-e89b-12d3-a456-426614174000
```

## Códigos de respuesta

- `200 OK` - Petición exitosa
- `201 Created` - Recurso creado exitosamente
- `401 Unauthorized` - Token inválido, expirado o faltante
- `403 Forbidden` - Usuario no tiene permisos (no es ADMIN)
- `404 Not Found` - Recurso no encontrado
- `500 Internal Server Error` - Error del servidor

## Implementación

El filtro `AuthFilter` intercepta todas las peticiones a `/api/*` y:

1. Verifica si la ruta es pública (permite el acceso)
2. Extrae el token del header `Authorization: Bearer {token}`
3. Valida el token consultando la base de datos
4. Verifica el rol del usuario si la ruta requiere ADMIN
5. Permite o deniega el acceso según las validaciones

## Personalización

Para agregar más rutas protegidas, edita `AuthFilter.java`:

```java
// Rutas que requieren ADMIN
private static final List<String> ADMIN_PATHS = Arrays.asList(
    "/api/users",
    "/api/categories"  // Agregar nueva ruta
);

// Rutas públicas
private static final List<String> PUBLIC_PATHS = Arrays.asList(
    "/api/auth/login",
    "/api/categories"  // Agregar nueva ruta pública
);
```
package com.grupo4.VetAndGo.spring;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    private final AuthFilter authFilter;

    public SecurityConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}

