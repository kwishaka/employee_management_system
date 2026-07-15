package com.ems.mis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {
    private Long id;
    private String trackId;
    private String fullName;
    private String email;
    private String status;
    private LocalDateTime submittedAt;
    private String resumeUrl;
    private String idDocumentUrl;
    private String message;
}