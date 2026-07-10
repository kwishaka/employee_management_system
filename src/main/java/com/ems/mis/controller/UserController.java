package com.ems.mis.controller;
import com.ems.mis.dto.LoginRequestDTO;
import com.ems.mis.dto.LoginResponseDTO;
import com.ems.mis.dto.UserRequestDTO;
import com.ems.mis.dto.UserResponseDTO;
import com.ems.mis.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public UserResponseDTO registerUser(@Valid @RequestBody UserRequestDTO dto) {
        return service.registerUser(dto);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(
            @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(service.loginUser(dto));
    }
}