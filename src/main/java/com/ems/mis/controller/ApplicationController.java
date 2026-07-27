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
import java.util.List;  // ✅ ADD THIS IMPORT

@Slf4j
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * FEATURE 1: Submit application with documents (multipart/form-data)
     * POST /api/applications/submit
     */
    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApplicationResponseDTO> submitApplication(
            @Valid @RequestPart("application") ApplicationRequestDTO request,
            @RequestPart(value = "resume", required = false) MultipartFile resume,
            @RequestPart(value = "idDocument", required = false) MultipartFile idDocument) throws IOException {

        log.info("📨 Received application submission from: {}", request.getEmail());

        ApplicationResponseDTO response = applicationService.submitApplication(request, resume, idDocument);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * FEATURE 1: Submit application (JSON only - without files)
     * POST /api/applications/create
     */
    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> createApplication(
            @Valid @RequestBody ApplicationRequestDTO request) throws IOException {

        log.info("📨 Received JSON application submission from: {}", request.getEmail());

        ApplicationResponseDTO response = applicationService.createApplication(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * FEATURE 2: Get application status by Tracking ID
     * GET /api/applications/track/{trackingId}
     */
    @GetMapping("/track/{trackingId}")
    public ResponseEntity<StatusResponseDTO> getApplicationStatus(@PathVariable String trackingId) {
        log.info("📊 Status check request for Tracking ID: {}", trackingId);

        StatusResponseDTO response = applicationService.getApplicationStatus(trackingId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get application by ID
     * GET /api/applications/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getApplication(@PathVariable Long id) {
        log.info("🔍 Fetching application with ID: {}", id);
        ApplicationResponseDTO response = applicationService.getApplication(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all applications
     * GET /api/applications
     */
    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> getAllApplications() {
        log.info("📋 Fetching all applications");
        List<ApplicationResponseDTO> responses = applicationService.getAllApplications();
        return ResponseEntity.ok(responses);
    }
}