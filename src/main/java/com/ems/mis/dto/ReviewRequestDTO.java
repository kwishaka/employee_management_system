package com.ems.mis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {

    @NotNull(message = "Decision is required")
    private String decision; // ADMITTED or REJECTED

    private String notes; // HR comments

    @NotBlank(message = "Reviewer name is required")
    private String reviewer;
}