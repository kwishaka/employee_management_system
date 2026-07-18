package com.ems.mis.controller;

import com.ems.mis.dto.ApplicationRequestDTO;
import com.ems.mis.dto.ApplicationResponseDTO;
import com.ems.mis.dto.StatusResponseDTO;
import com.ems.mis.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
     * Content-Type: multipart/form-data
     */
    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApplicationResponseDTO> submitApplication(
            @Valid @RequestPart("application") ApplicationRequestDTO request,
            @RequestPart(value = "resume", required = false) MultipartFile resume,
            @RequestPart(value = "idDocument", required = false) MultipartFile idDocument) throws IOException {

        log.info("📨 Received application submission from: {}", request.getEmail());
        log.info("Resume file: {}", resume != null ? resume.getOriginalFilename() : "null");
        log.info("ID Document file: {}", idDocument != null ? idDocument.getOriginalFilename() : "null");

        ApplicationResponseDTO response = applicationService.submitApplication(request, resume, idDocument);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * FEATURE 2: Get application status by Track ID
     * GET /api/applications/track/{trackId}
     */
    @GetMapping("/track/{trackId}")
    public ResponseEntity<StatusResponseDTO> getApplicationStatus(@PathVariable String trackId) {
        log.info("📊 Status check request for Track ID: {}", trackId);

        StatusResponseDTO response = applicationService.getApplicationStatus(trackId);
        return ResponseEntity.ok(response);
    }
}