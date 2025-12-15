package com.grupo4.VetAndGo.domain.infrastructura;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.grupo4.VetAndGo.domain.service.PasswordEncoderService;

public class PasswordEncoderImpl implements PasswordEncoderService {
    @Override
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }

    @Override
    public boolean verify(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
