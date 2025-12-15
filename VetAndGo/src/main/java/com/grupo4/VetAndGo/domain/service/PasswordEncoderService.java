package com.grupo4.VetAndGo.domain.service;

public interface PasswordEncoderService {
    String encode(String rawPassword);
    boolean verify(String rawPassword, String encodedPassword);
}
