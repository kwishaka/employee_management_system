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
public class StatusResponseDTO {
    private String trackingId;
    private String fullName;
    private String email;          // ✅ Add this field
    private String status;
    private String statusDescription;
    private LocalDateTime appliedDate;
    private LocalDateTime reviewedAt;
    private String hrNotes;
    private String message;
}