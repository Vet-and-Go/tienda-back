package com.grupo4.VetAndGo.spring;

import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.domain.service.TokenUtils;
import com.grupo4.VetAndGo.spring.annotation.RequireAdmin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

  private final RequestMappingHandlerMapping handlerMapping;
  private final TokenUtils tokenUtils;

  public AuthFilter(RequestMappingHandlerMapping handlerMapping, TokenUtils tokenUtils) {
    this.handlerMapping = handlerMapping;
    this.tokenUtils = tokenUtils;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    package com.vetandgo.tienda.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        
        // ⭐ AGREGAR ESTAS LÍNEAS DE CORS ⭐
        response.setHeader("Access-Control-Allow-Origin", "http://vetandgo-store-front.preproducciondaw.cip.fpmislata.com");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        
        // Manejar peticiones OPTIONS (preflight)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return; // No continuar con el filtro
        }
        
        // Tu lógica de autenticación existente aquí...
        // ...
        
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización si es necesaria
    }

    @Override
    public void destroy() {
        // Limpieza si es necesaria
    }
}

    try {
      HandlerExecutionChain handlerChain = handlerMapping.getHandler(request);

      if (handlerChain != null
          && handlerChain.getHandler() instanceof HandlerMethod handlerMethod
          && handlerMethod.hasMethodAnnotation(RequireAdmin.class)) {

        String token = extractToken(request);
        if (token == null) {
          sendError(response, HttpServletResponse.SC_UNAUTHORIZED,
              "Missing or invalid token");
          return;
        }

        User user = tokenUtils.getUserbFromToken(token);
        if (user == null) {
          sendError(response, HttpServletResponse.SC_UNAUTHORIZED,
              "Missing or invalid token");
          return;
        }

        if (user.getRole() != Role.ADMIN) {
          sendError(response, HttpServletResponse.SC_FORBIDDEN, "Admin role required");
          return;
        }
      }
    } catch (Exception e) {
      // let it fall through
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      return token;
    }
    return null;
  }

  private void sendError(HttpServletResponse response, int status, String message) throws IOException {
    response.setStatus(status);
    response.setContentType("application/json");
    response.getWriter().write("{\"error\": \"" + message + "\"}");
  }
}
