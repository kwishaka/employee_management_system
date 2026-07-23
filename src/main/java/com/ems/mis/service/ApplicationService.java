package com.ems.mis.service;


import com.ems.mis.dto.AdminApplicationResponseDTO;
import com.ems.mis.dto.ApplicationRequestDTO;
import com.ems.mis.dto.ApplicationResponseDTO;
import com.ems.mis.dto.StatusResponseDTO;
import com.ems.mis.entry.Application;
import com.ems.mis.entry.ApplicationStatus;
import com.ems.mis.exception.ApplicationNotFoundException;
import com.ems.mis.exception.InvalidStatusException;
import com.ems.mis.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final FileStorageService fileStorageService;

    // ========================================
    // FEATURE 1: SUBMIT APPLICATION
    // ========================================

    @Transactional
    public ApplicationResponseDTO submitApplication(
            ApplicationRequestDTO request,
            MultipartFile resume,
            MultipartFile idDocument) throws IOException {

        log.info("📝 Processing application submission for: {}", request.getEmail());

        if (applicationRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered. Please use a different email.");
        }

        String resumeUrl = null;
        String idDocumentUrl = null;

        if (resume != null && !resume.isEmpty()) {
            resumeUrl = fileStorageService.storeFile(resume, "resume_" + request.getEmail());
            log.info("✅ Resume stored: {}", resumeUrl);
        }

        if (idDocument != null && !idDocument.isEmpty()) {
            idDocumentUrl = fileStorageService.storeFile(idDocument, "id_" + request.getEmail());
            log.info("✅ ID Document stored: {}", idDocumentUrl);
        }

        String trackingId = generateTrackingId();

        Application application = new Application();
        application.setTrackingId(trackingId);
        application.setFullName(request.getFullName());
        application.setEmail(request.getEmail());
        application.setPhone(request.getPhone());
        application.setPosition(request.getPosition());
        application.setStatus(ApplicationStatus.PENDING);
        application.setAppliedDate(LocalDateTime.now());

        Application saved = applicationRepository.save(application);

        log.info("✅ Application submitted! Tracking ID: {}", trackingId);

        ApplicationResponseDTO response = mapToResponseDTO(saved);
        response.setMessage("Application submitted successfully! Your Tracking ID is: " + trackingId);
        return response;
    }

    @Transactional
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO request) {
        log.info("📝 Creating new application for: {}", request.getFullName());

        if (applicationRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered. Please use a different email.");
        }

        String trackingId = generateTrackingId();

        Application application = new Application();
        application.setTrackingId(trackingId);
        application.setFullName(request.getFullName());
        application.setEmail(request.getEmail());
        application.setPhone(request.getPhone());
        application.setPosition(request.getPosition());
        application.setStatus(ApplicationStatus.PENDING);
        application.setAppliedDate(LocalDateTime.now());

        Application saved = applicationRepository.save(application);

        ApplicationResponseDTO response = mapToResponseDTO(saved);
        response.setMessage("Application created successfully! Tracking ID: " + trackingId);
        return response;
    }

    // ========================================
    // FEATURE 2: GET APPLICATIONS
    // ========================================

    public ApplicationResponseDTO getApplication(Long id) {
        log.info("🔍 Fetching application with id: {}", id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
        return mapToResponseDTO(application);
    }

    public ApplicationResponseDTO getApplicationByTrackingId(String trackingId) {
        log.info("🔍 Fetching application with tracking ID: {}", trackingId);
        Application application = applicationRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with tracking ID: " + trackingId));
        return mapToResponseDTO(application);
    }

    public StatusResponseDTO getApplicationStatus(String trackingId) {
        log.info("🔍 Checking status for Tracking ID: {}", trackingId);

        Application application = applicationRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with tracking ID: " + trackingId));

        return StatusResponseDTO.builder()
                .trackingId(application.getTrackingId())
                .fullName(application.getFullName())
                .email(application.getEmail())
                .status(application.getStatus().name())
                .statusDescription(application.getStatus().getDescription())
                .appliedDate(application.getAppliedDate())
                .reviewedAt(application.getReviewedAt())
                .hrNotes(application.getHrNotes())
                .message("Application status retrieved successfully")
                .build();
    }

    public List<ApplicationResponseDTO> getAllApplications() {
        log.info("📋 Fetching all applications");
        return applicationRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ========================================
    // FEATURE 3: HR ADMIN METHODS
    // ========================================

    // ✅ GET ALL APPLICATIONS FOR ADMIN
    public List<AdminApplicationResponseDTO> getAllApplicationsForAdmin() {
        log.info("📋 Admin: Fetching all applications");
        return applicationRepository.findAll().stream()
                .map(this::mapToAdminDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET APPLICATIONS BY STATUS
    public List<AdminApplicationResponseDTO> getApplicationsByStatusForAdmin(String status) {
        log.info("📋 Admin: Fetching applications with status: {}", status);

        ApplicationStatus applicationStatus;
        try {
            applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Invalid status. Allowed: PENDING, REVIEWED, ADMITTED, REJECTED");
        }

        return applicationRepository.findByStatus(applicationStatus).stream()
                .map(this::mapToAdminDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET APPLICATION BY ID FOR ADMIN
    public AdminApplicationResponseDTO getApplicationByIdForAdmin(Long id) {
        log.info("🔍 Admin: Fetching application with ID: {}", id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with ID: " + id));
        return mapToAdminDTO(application);
    }

    // ✅ REVIEW APPLICATION (Approve/Reject)
    @Transactional
    public AdminApplicationResponseDTO reviewApplication(Long id, String decision, String notes, String reviewer) {
        log.info("📝 Reviewing application ID: {} by: {}", id, reviewer);

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with ID: " + id));

        if (!application.getStatus().isReviewable()) {
            throw new InvalidStatusException(
                    "Application cannot be reviewed. Current status: " + application.getStatus()
            );
        }

        ApplicationStatus newStatus;
        try {
            newStatus = ApplicationStatus.valueOf(decision.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid decision. Allowed: ADMITTED, REJECTED");
        }

        if (!newStatus.isFinal()) {
            throw new IllegalArgumentException("Decision must be ADMITTED or REJECTED");
        }

        application.setStatus(newStatus);
        application.setHrNotes(notes);
        application.setReviewedAt(LocalDateTime.now());
        application.setReviewedBy(reviewer);

        Application updated = applicationRepository.save(application);
        log.info("✅ Application {} reviewed - Decision: {}", application.getTrackingId(), newStatus);

        return mapToAdminDTO(updated);
    }

    // ✅ DELETE APPLICATION
    @Transactional
    public void deleteApplication(Long id) {
        log.info("🗑️ Deleting application with id: {}", id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
        applicationRepository.delete(application);
        log.info("✅ Application deleted successfully!");
    }

    // ✅ GET APPLICATION STATISTICS
    public Map<String, Object> getApplicationStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", applicationRepository.count());
        stats.put("pending", applicationRepository.countByStatus(ApplicationStatus.PENDING));
        stats.put("reviewed", applicationRepository.countByStatus(ApplicationStatus.REVIEWED));
        stats.put("admitted", applicationRepository.countByStatus(ApplicationStatus.ADMITTED));
        stats.put("rejected", applicationRepository.countByStatus(ApplicationStatus.REJECTED));
        stats.put("timestamp", LocalDateTime.now());
        return stats;
    }

    // ========================================
    // HELPER METHODS
    // ========================================

    private AdminApplicationResponseDTO mapToAdminDTO(Application application) {
        return AdminApplicationResponseDTO.builder()
                .id(application.getId())
                .trackingId(application.getTrackingId())
                .fullName(application.getFullName())
                .email(application.getEmail())
                .phone(application.getPhone())
                .position(application.getPosition())
                .status(application.getStatus().name())
                .statusDescription(application.getStatus().getDescription())
                .hrNotes(application.getHrNotes())
                .reviewedBy(application.getReviewedBy())
                .appliedDate(application.getAppliedDate())
                .reviewedAt(application.getReviewedAt())
                .message("Application retrieved successfully")
                .build();
    }

    private ApplicationResponseDTO mapToResponseDTO(Application application) {
        ApplicationResponseDTO response = new ApplicationResponseDTO();
        response.setId(application.getId());
        response.setTrackingId(application.getTrackingId());
        response.setFullName(application.getFullName());
        response.setEmail(application.getEmail());
        response.setPhone(application.getPhone());
        response.setPosition(application.getPosition());
        response.setStatus(application.getStatus().name());
        response.setAppliedDate(application.getAppliedDate());
        return response;
    }


    private String generateTrackingId() {
        return "APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}