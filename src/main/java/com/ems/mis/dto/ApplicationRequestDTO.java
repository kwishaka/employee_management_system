package com.ems.mis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "National ID is required")
    @Pattern(regexp = "^[0-9]{9,}$", message = "National ID must contain only numbers (min 9 digits)")
    private String nationalId;

    private String phone;

    @NotBlank(message = "Education details are required")
    private String education;

    private String workExperience;

    // Base64 encoded files
    private String resumeBase64;
    private String idDocumentBase64;

    // File metadata
    private String resumeFileName;
    private String idDocumentFileName;
    private String resumeContentType;
    private String idDocumentContentType;
}