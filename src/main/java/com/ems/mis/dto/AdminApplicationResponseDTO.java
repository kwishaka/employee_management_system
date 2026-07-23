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
public class AdminApplicationResponseDTO {
    private Long id;
    private String trackingId;
    private String fullName;
    private String email;
    private String phone;
    private String position;
    private String status;
    private String statusDescription;
    private String hrNotes;
    private String reviewedBy;
    private LocalDateTime appliedDate;
    private LocalDateTime reviewedAt;
    private String message;
}
