package com.ems.mis.dto;
<<<<<<< HEAD
=======

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
import jakarta.validation.constraints.NotBlank;
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
public class LoginRequestDTO {
<<<<<<< HEAD
    @NotBlank(message = "Username is required")
    private String username;
=======

    @NotBlank(message = "Username is required")
    private String username;

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
    @NotBlank(message = "Password is required")
    private String password;
}
