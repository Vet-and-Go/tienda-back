package com.vetandgo.tienda.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1) // Se ejecuta primero
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        
        // ==========================================
        // CONFIGURACIÓN DE CORS
        // ==========================================
        response.setHeader("Access-Control-Allow-Origin", "http://vetandgo-store-front.preproducciondaw.cip.fpmislata.com");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Accept, Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        
        // Manejar peticiones OPTIONS (preflight)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return; // No continuar con el filtro
        }
        
        // ==========================================
        // LÓGICA DE AUTENTICACIÓN
        // ==========================================
        
        String path = request.getRequestURI();
        String method = request.getMethod();
        
        // Rutas públicas que NO requieren autenticación
        if (isPublicRoute(path, method)) {
            chain.doFilter(req, res);
            return;
        }
        
        // Obtener el token del header Authorization
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token no proporcionado\"}");
            return;
        }
        
        String token = authHeader.substring(7); // Remover "Bearer "
        
        // Validar el token
        if (!isValidToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido o expirado\"}");
            return;
        }
        
        // Si el token es válido, continuar
        chain.doFilter(req, res);
    }
    
    /**
     * Define qué rutas son públicas y no requieren autenticación
     */
    private boolean isPublicRoute(String path, String method) {
        // Rutas públicas - ajusta según tu aplicación
        return path.startsWith("/api/products") && method.equals("GET") ||
               path.startsWith("/api/categories") && method.equals("GET") ||
               path.equals("/api/auth/login") ||
               path.equals("/api/auth/register") ||
               path.startsWith("/public/");
    }
    
    /**
     * Valida el token JWT
     * NOTA: Implementa tu lógica real de validación JWT aquí
     */
    private boolean isValidToken(String token) {
        // TODO: Implementar validación real con JWT
        // Ejemplo con una librería como jjwt:
        /*
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
        */
        
        // Temporalmente, permitir todos los tokens para testing
        return token != null && !token.isEmpty();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro si es necesaria
    }

    @Override
    public void destroy() {
        // Limpieza de recursos si es necesaria
    }
}
