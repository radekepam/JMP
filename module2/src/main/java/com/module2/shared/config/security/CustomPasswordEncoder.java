package com.module2.shared.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomPasswordEncoder {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(10);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }

    public static String encodePassword(String password) {
        return PASSWORD_ENCODER.encode(password);
    }
}
