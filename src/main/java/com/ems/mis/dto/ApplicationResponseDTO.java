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
    private String trackingId;  // ✅ Add this field
    private String fullName;
    private String email;
    private String phone;
    private String position;
    private String status;
    private LocalDateTime appliedDate;
    private String message;
}