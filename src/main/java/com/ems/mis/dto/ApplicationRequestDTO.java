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
    private String position;
    private Long positionId;
    private String education;
    private String workExperience;
}
                                                                                                                                                                                                                                                                        