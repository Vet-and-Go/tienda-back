package com.grupo4.VetAndGo.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import com.grupo4.VetAndGo.domain.infrastructura.PasswordEncoderImpl;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.domain.repository.OrderRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.repository.TokenUtilsRepository;
import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.domain.service.CategoryService;
import com.grupo4.VetAndGo.domain.service.OrderService;
import com.grupo4.VetAndGo.domain.service.PasswordEncoderService;
import com.grupo4.VetAndGo.domain.service.PaymentService;
import com.grupo4.VetAndGo.domain.service.ProductService;
import com.grupo4.VetAndGo.domain.service.TokenUtils;
import com.grupo4.VetAndGo.domain.service.UserService;
import com.grupo4.VetAndGo.domain.service.impl.CategoryServiceImpl;
import com.grupo4.VetAndGo.domain.service.impl.OrderServiceImpl;
import com.grupo4.VetAndGo.domain.service.impl.PaymentServiceImpl;
import com.grupo4.VetAndGo.domain.service.impl.ProductServiceImpl;
import com.grupo4.VetAndGo.domain.service.impl.TokenUtilsImpl;
import com.grupo4.VetAndGo.domain.service.impl.UserServiceImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.CategoryJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.OrderJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.ProductJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.TokenUtilsJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.UserJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.CategoryJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.OrderJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.ProductJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.TokenUtilsJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.UserJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.repository.impl.CategoryRepositoryImpl;
import com.grupo4.VetAndGo.persistence.repository.impl.OrderRepositoryImpl;
import com.grupo4.VetAndGo.persistence.repository.impl.ProductRepositoryImpl;
import com.grupo4.VetAndGo.persistence.repository.impl.TokenUtilsRepositoryImpl;
import com.grupo4.VetAndGo.persistence.repository.impl.UserRepositoryImpl;

@Configuration
@Profile("!test")
@EnableJpaRepositories(basePackages = "com.grupo4.VetAndGo.persistence.dao.jpa")
@EntityScan(basePackages = "com.grupo4.VetAndGo.persistence.dao.jpa.entity")
public class SpringConfig {
  @Bean
  OrderRepository orderRepository(OrderJpaDao orderJpaDao) {
    return new OrderRepositoryImpl(orderJpaDao);
  }

  @Bean
  OrderService orderService(OrderRepository orderRepository, UserRepository userRepository,
      ProductRepository productRepository, PaymentService paymentService) {
    return new OrderServiceImpl(orderRepository, productRepository, userRepository, paymentService);
  }

  @Bean
  OrderJpaDao orderJpaDao() {
    return new OrderJpaDaoImpl();
  }

  @Bean
  public CategoryRepository categoryRepository(CategoryJpaDao categoryJpaDao) {
    return new CategoryRepositoryImpl(categoryJpaDao);
  }

  @Bean
  public CategoryService categoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
    return new CategoryServiceImpl(categoryRepository, productRepository);
  }

  @Bean
  public CategoryJpaDao categoryJpaDao() {

    return new CategoryJpaDaoImpl();
  }

  @Bean
  public UserRepository userRepository(UserJpaDao userJpaDao) {
    return new UserRepositoryImpl(userJpaDao);
  }

  @Bean
  public UserJpaDao userJpaDao() {
    return new UserJpaDaoImpl();
  }

  @Bean
  public ProductService productService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    return new ProductServiceImpl(productRepository, categoryRepository);
  }

  @Bean
  public ProductJpaDao productJpaDao() {
    return new ProductJpaDaoImpl();
  }

  @Bean
  public ProductRepository productRepository(ProductJpaDao productJpaDao) {
    return new ProductRepositoryImpl(productJpaDao);
  }

  @Bean
  public PasswordEncoderService passwordEncoderService() {
    return new PasswordEncoderImpl();
  }

  @Bean
  public UserService userService(UserRepository userRepository, PasswordEncoderService passwordEncoderService,
      TokenUtilsRepository token) {
    return new UserServiceImpl(passwordEncoderService, token, userRepository);
  }

  @Bean
  public TokenUtils tokenUtils(TokenUtilsRepository tokenUtilsRepository) {
    return new TokenUtilsImpl(tokenUtilsRepository);
  }

  @Bean
  TokenUtilsRepository tokenUtilsRepository(TokenUtilsJpaDao tokenUtilsJpaDao) {
    return new TokenUtilsRepositoryImpl(tokenUtilsJpaDao);
  }

  @Bean
  TokenUtilsJpaDao tokenUtilsJpaDao() {
    return new TokenUtilsJpaDaoImpl();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public PaymentService paymentService(com.grupo4.VetAndGo.nanoServices.payment.PaymentService nanoPaymentService) {
    return new PaymentServiceImpl(nanoPaymentService);
  }
  
  @Bean
  public com.grupo4.VetAndGo.nanoServices.payment.PaymentService nanoPaymentService(RestTemplate restTemplate) {
      return new com.grupo4.VetAndGo.nanoServices.payment.impl.PaymentServiceImpl(restTemplate);
  }

}