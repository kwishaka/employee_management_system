package com.ems.mis.service;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
@Service
public class TokenService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();
    public String generateToken() {
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return token.toString();
    }

}



