package com.ems.mis.controller;
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
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
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
<<<<<<< HEAD
    private final AuthService authService;
=======

    private final AuthService authService;

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
