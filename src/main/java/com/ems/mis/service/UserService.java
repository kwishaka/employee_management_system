package com.ems.mis.service;

import com.ems.mis.dto.UserRequestDTO;
import com.ems.mis.dto.UserResponseDTO;
import com.ems.mis.entry.User;
import com.ems.mis.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // Register User
    public UserResponseDTO registerUser(UserRequestDTO dto) {

        if (repository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setFullName(dto.getFullName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        User savedUser = repository.save(user);
        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    // Get all users
    public List<UserResponseDTO> getAllUsers() {

        return repository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getFullName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()))
                .collect(Collectors.toList());

    }
}