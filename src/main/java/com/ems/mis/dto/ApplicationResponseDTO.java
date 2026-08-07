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
    private String trackingId;      
=======
    private String trackingId;  // ✅ Add this field
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
    private String fullName;
    private String email;
    private String phone;
    private String position;
    private String status;
    private LocalDateTime appliedDate;
    private String message;
}