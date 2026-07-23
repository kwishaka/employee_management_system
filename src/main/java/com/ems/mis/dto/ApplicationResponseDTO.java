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
<<<<<<< HEAD
    private String trackingId;      // ✅ Add this field
=======
    private String trackingId;  // ✅ Add this field
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    private String fullName;
    private String email;
    private String phone;
    private String position;
    private String status;
    private LocalDateTime appliedDate;
    private String message;
}