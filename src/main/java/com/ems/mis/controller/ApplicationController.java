package com.ems.mis.controller;

import com.ems.mis.dto.ApplicationRequestDTO;
import com.ems.mis.dto.ApplicationResponseDTO;
import com.ems.mis.dto.ResponseDTO;
import com.ems.mis.dto.StatusResponseDTO;
import com.ems.mis.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);
    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ApplicationResponseDTO>> createApplication(
            @Valid @RequestBody ApplicationRequestDTO requestDTO) {
        log.info("Creating new application");
        ApplicationResponseDTO response = applicationService.createApplication(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.success("Application created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ApplicationResponseDTO>> getApplication(@PathVariable Long id) {
        log.info("Fetching application with id: {}", id);
        ApplicationResponseDTO response = applicationService.getApplication(id);
        return ResponseEntity.ok(ResponseDTO.success("Application retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ApplicationResponseDTO>>> getAllApplications() {
        log.info("Fetching all applications");
        List<ApplicationResponseDTO> responses = applicationService.getAllApplications();
        return ResponseEntity.ok(ResponseDTO.success("Applications retrieved successfully", responses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ApplicationResponseDTO>> updateApplication(
            @PathVariable Long id, @Valid @RequestBody ApplicationRequestDTO requestDTO) {
        log.info("Updating application with id: {}", id);
        ApplicationResponseDTO response = applicationService.updateApplication(id, requestDTO);
        return ResponseEntity.ok(ResponseDTO.success("Application updated successfully", response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseDTO<StatusResponseDTO>> updateApplicationStatus(
            @PathVariable Long id, @RequestParam String status) {
        log.info("Updating status for application id: {} to: {}", id, status);
        StatusResponseDTO response = applicationService.updateApplicationStatus(id, status);
        return ResponseEntity.ok(ResponseDTO.success("Application status updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteApplication(@PathVariable Long id) {
        log.info("Deleting application with id: {}", id);
        applicationService.deleteApplication(id);
        return ResponseEntity.ok(ResponseDTO.success("Application deleted successfully", null));
    }
}