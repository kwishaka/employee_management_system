package com.ems.mis.controller;
import com.ems.mis.dto.AuthRequestDTO;
import com.ems.mis.dto.AuthResponseDTO;
import com.ems.mis.dto.LoginRequestDTO;
import com.ems.mis.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/login/jwt")
    public ResponseEntity<Map<String, Object>> loginWithJwt(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.loginWithJwt(request));
    }

}



