package com.ems.mis.controller;

import com.ems.mis.dto.AdminApplicationResponseDTO;
import com.ems.mis.dto.ReviewRequestDTO;
import com.ems.mis.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_ADMIN')")
public class AdminController {

    private final ApplicationService applicationService;

    @GetMapping("/applications")
    public ResponseEntity<List<AdminApplicationResponseDTO>> getAllApplications() {
        log.info("📋 Admin: Fetching all applications");
        return ResponseEntity.ok(applicationService.getAllApplicationsForAdmin());
    }

    @GetMapping("/applications/status/{status}")
    public ResponseEntity<List<AdminApplicationResponseDTO>> getApplicationsByStatus(
            @PathVariable String status) {
        log.info("📋 Admin: Fetching applications with status: {}", status);
        return ResponseEntity.ok(applicationService.getApplicationsByStatusForAdmin(status));
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<AdminApplicationResponseDTO> getApplicationById(
            @PathVariable Long id) {
        log.info("🔍 Admin: Fetching application ID: {}", id);
        return ResponseEntity.ok(applicationService.getApplicationByIdForAdmin(id));
    }

    @PutMapping("/applications/{id}/review")
    public ResponseEntity<AdminApplicationResponseDTO> reviewApplication(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequestDTO reviewRequest) {
        log.info(" Admin: Reviewing application ID: {} by: {}", id, reviewRequest.getReviewer());
        return ResponseEntity.ok(applicationService.reviewApplication(
                id,
                reviewRequest.getDecision(),
                reviewRequest.getNotes(),
                reviewRequest.getReviewer()
        ));
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.info("🗑 Admin: Deleting application ID: {}", id);
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getApplicationStats() {
        log.info(" Admin: Fetching application statistics");
        return ResponseEntity.ok(applicationService.getApplicationStats());
    }
}