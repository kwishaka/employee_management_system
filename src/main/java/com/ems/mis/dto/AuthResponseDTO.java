package com.ems.mis.dto;
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String refreshToken;
    private String username;
    private String email;
    private String fullName;
    private String role;
    private Long userId;
    private String message;
}
