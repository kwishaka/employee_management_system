package com.ems.mis.controller;

import com.ems.mis.dto.ApplicationRequestDTO;
import com.ems.mis.dto.ApplicationResponseDTO;
import com.ems.mis.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * FEATURE 1: Submit application with documents
     * POST /api/applications/submit
     *
     * Request: multipart/form-data
     * - application: JSON with applicant details
     * - resume: PDF/DOC/DOCX/JPG/PNG file
     * - idDocument: PDF/DOC/DOCX/JPG/PNG file
     */
    @PostMapping("/submit")
    public ResponseEntity<ApplicationResponseDTO> submitApplication(
            @Valid @RequestPart("application") ApplicationRequestDTO request,
            @RequestPart("resume") MultipartFile resume,
            @RequestPart("idDocument") MultipartFile idDocument) throws IOException {

        log.info("📨 Received application submission from: {}", request.getEmail());

        ApplicationResponseDTO response = applicationService.submitApplication(
                request,
                resume,
                idDocument
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
