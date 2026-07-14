package com.ems.mis.service;

import com.ems.mis.dto.LoginRequestDTO;
import com.ems.mis.dto.LoginResponseDTO;
import com.ems.mis.entry.User;
import com.ems.mis.repository.UserRepository;
import com.ems.mis.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());

        LoginResponseDTO response = new LoginResponseDTO();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        // We'll add setToken() after updating LoginResponseDTO.
        response.setMessage(token);

        return response;
    }
}
