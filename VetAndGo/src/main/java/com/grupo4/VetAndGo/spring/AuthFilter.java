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
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // response.setHeader("Access-Control-Allow-Origin", "http://localhost:4202");
    // response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,
    // OPTIONS");
    // response.setHeader("Access-Control-Allow-Headers", "Content-Type,
    // Authorization");
    // response.setHeader("Access-Control-Allow-Credentials", "true");

    String requestMethod = request.getMethod();

    if ("OPTIONS".equalsIgnoreCase(requestMethod)) {
      System.out.println("OPTIONS request, permitir sin validación");
      filterChain.doFilter(request, response);
      return;
    }

    try {
      HandlerExecutionChain handlerChain = handlerMapping.getHandler(request);

      if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod handlerMethod
          && handlerMethod.hasMethodAnnotation(RequireAdmin.class)) {

        try {
          String token = extractToken(request);
          if (token == null) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
            return;
          }

          User user = tokenUtils.getUserbFromToken(token);
          if (user == null) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
            return;
          }

          if (user.getRole() != Role.ADMIN) {
            sendError(response, HttpServletResponse.SC_FORBIDDEN, "Admin role required");
            return;
          }
        } catch (IllegalArgumentException e) {
          sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
          return;
        } catch (Exception e) {
          sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Authentication error");
          return;
        }
      }
    } catch (Exception e) {
      // Ignore errors in handler mapping to allow request to proceed to 404 or other
      // handlers
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
