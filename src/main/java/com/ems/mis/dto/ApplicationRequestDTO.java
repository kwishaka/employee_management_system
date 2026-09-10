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
    private String phone;
    private String position;         // ✅ ADD THIS (if missing)
    private Long positionId;         // ✅ ADD THIS (for repository lookup)
    private String education;
    private String workExperience;
}
                                                                                                                                                                                                                                                                        