package com.grupo4.VetAndGo.domain.infrastructura;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(
            "http://localhost:3000",
            "http://localhost:4200",
            "http://vetandgo-bank-front.preproducciondaw.cip.fpmislata.com",
            "http://vetandgo-store-front.preproducciondaw.cip.fpmislata.com")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
        .allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "Accept")
        .allowCredentials(true)
        .maxAge(3600);
  }
}
