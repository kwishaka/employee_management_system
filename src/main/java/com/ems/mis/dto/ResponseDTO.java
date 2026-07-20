package com.ems.mis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    private String status;
    private String message;
    private T data;

    public static <T> ResponseDTO<T> success(String message, T data) {
        return ResponseDTO.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseDTO<T> error(String message) {
        return ResponseDTO.<T>builder()
                .status("error")
                .message(message)
                .data(null)
                .build();
    }
}