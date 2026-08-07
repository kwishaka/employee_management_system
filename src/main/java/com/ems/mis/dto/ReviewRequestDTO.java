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
<<<<<<< HEAD
    private String decision;
    private String notes;
=======
    private String decision; 

    private String notes; 

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
    @NotBlank(message = "Reviewer name is required")
    private String reviewer;
}
