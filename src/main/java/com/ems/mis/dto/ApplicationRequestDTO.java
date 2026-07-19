package com.ems.mis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequestDTO {

    private String fullName;
    private String email;
    private String nationalId;
    private String phone;
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