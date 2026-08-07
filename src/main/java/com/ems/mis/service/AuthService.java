package com.ems.mis.service;
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
import com.ems.mis.dto.AuthRequestDTO;
import com.ems.mis.dto.AuthResponseDTO;
import com.ems.mis.dto.LoginRequestDTO;
import com.ems.mis.entry.User;
import com.ems.mis.entry.UserRole;
import com.ems.mis.repository.UserRepository;
import com.ems.mis.security.config.CustomTokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
<<<<<<< HEAD
    private final CustomTokenAuthenticationFilter customTokenAuthFilter; // ADD THIS
=======
    private final CustomTokenAuthenticationFilter tokenFilter;
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715

    @Transactional
    public AuthResponseDTO register(AuthRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
<<<<<<< HEAD
        }
        UserRole role = UserRole.HR_ADMIN;
=======
        }"
        UserRole role = UserRole.APPLICANT;  
        if ("hr_admin".equalsIgnoreCase(request.getUsername()) ||
                request.getEmail().toLowerCase().contains("admin")) {
            role = UserRole.HR_ADMIN;  
        }
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
<<<<<<< HEAD
                .role(UserRole.HR_ADMIN)
=======
                .role(role)  
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
                .build();

        userRepository.save(user);

        String token = tokenService.generateToken();
<<<<<<< HEAD
        customTokenAuthFilter.storeToken(token, user.getUsername()); // ADD THIS
=======
        tokenFilter.storeToken(token, user.getUsername());
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715

        return AuthResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .userId(user.getId())
                .message("Registration successful")
                .build();
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = tokenService.generateToken();
<<<<<<< HEAD
        customTokenAuthFilter.storeToken(token, user.getUsername()); // ADD THIS
=======
        tokenFilter.storeToken(token, user.getUsername());
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715

        return AuthResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .userId(user.getId())
                .message("Login successful")
                .build();
<<<<<<< HEAD
        }

    }




=======
    }
}
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
