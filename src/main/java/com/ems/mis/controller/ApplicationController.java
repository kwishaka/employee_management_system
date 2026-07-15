package com.ems.mis.controller;
import com.ems.mis.exception.ApplicationNotFoundException;
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
     * Content-Type: application/json
     */
    @PostMapping(value = "/submit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationResponseDTO> submitApplication(
            @Valid @RequestBody ApplicationRequestDTO request) throws IOException {

        log.info("📨 Received application submission from: {}", request.getEmail());

        ApplicationResponseDTO response = applicationService.submitApplication(request);

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