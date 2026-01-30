package com.grupo4.VetAndGo.domain.infrastructura;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns(
            "http://localhost:*",
            "https://localhost:*",
            "http://vetandgo-bank-front.preproducciondaw.cip.fpmislata.com",
            "http://vetandgo-store-front.preproducciondaw.cip.fpmislata.com",
            "https://vetandgo-bank-front.preproducciondaw.cip.fpmislata.com",
            "https://vetandgo-store-front.preproducciondaw.cip.fpmislata.com")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
  }
}
