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

    private String trackId;
    private String fullName;
    private String email;
    private String status;
    private String statusDescription;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
    private String hrNotes;
    private String message;

}
