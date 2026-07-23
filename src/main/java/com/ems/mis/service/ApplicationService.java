package com.ems.mis.service;

<<<<<<< HEAD

=======
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
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

<<<<<<< HEAD
=======
    /**
     * Submit application with files (multipart/form-data)
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    @Transactional
    public ApplicationResponseDTO submitApplication(
            ApplicationRequestDTO request,
            MultipartFile resume,
            MultipartFile idDocument) throws IOException {

        log.info("📝 Processing application submission for: {}", request.getEmail());

<<<<<<< HEAD
=======
        // Check if email already exists
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
        if (applicationRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered. Please use a different email.");
        }

<<<<<<< HEAD
=======
        // Store files if provided
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
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

<<<<<<< HEAD
        String trackingId = generateTrackingId();

=======
        // Generate tracking ID
        String trackingId = generateTrackingId();

        // Create application
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
        Application application = new Application();
        application.setTrackingId(trackingId);
        application.setFullName(request.getFullName());
        application.setEmail(request.getEmail());
        application.setPhone(request.getPhone());
        application.setPosition(request.getPosition());
        application.setStatus(ApplicationStatus.PENDING);
        application.setAppliedDate(LocalDateTime.now());

<<<<<<< HEAD
=======
        // Save to database
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
        Application saved = applicationRepository.save(application);

        log.info("✅ Application submitted! Tracking ID: {}", trackingId);

<<<<<<< HEAD
=======
        // Build response
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
        ApplicationResponseDTO response = mapToResponseDTO(saved);
        response.setMessage("Application submitted successfully! Your Tracking ID is: " + trackingId);
        return response;
    }

<<<<<<< HEAD
=======
    /**
     * Create application without files (JSON only)
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    @Transactional
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO request) {
        log.info("📝 Creating new application for: {}", request.getFullName());

<<<<<<< HEAD
=======
        // Check if email already exists
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
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

<<<<<<< HEAD
=======
    /**
     * Get application by ID
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    public ApplicationResponseDTO getApplication(Long id) {
        log.info("🔍 Fetching application with id: {}", id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
        return mapToResponseDTO(application);
    }

<<<<<<< HEAD
=======
    /**
     * Get application by Tracking ID
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    public ApplicationResponseDTO getApplicationByTrackingId(String trackingId) {
        log.info("🔍 Fetching application with tracking ID: {}", trackingId);
        Application application = applicationRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with tracking ID: " + trackingId));
        return mapToResponseDTO(application);
    }

<<<<<<< HEAD
=======
    /**
     * Get application status by Tracking ID (for applicants)
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
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

<<<<<<< HEAD
=======
    /**
     * Get all applications
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    public List<ApplicationResponseDTO> getAllApplications() {
        log.info("📋 Fetching all applications");
        return applicationRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ========================================
    // FEATURE 3: HR ADMIN METHODS
    // ========================================

<<<<<<< HEAD
    // ✅ GET ALL APPLICATIONS FOR ADMIN
=======
    /**
     * Get all applications for admin
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    public List<AdminApplicationResponseDTO> getAllApplicationsForAdmin() {
        log.info("📋 Admin: Fetching all applications");
        return applicationRepository.findAll().stream()
                .map(this::mapToAdminDTO)
                .collect(Collectors.toList());
    }

<<<<<<< HEAD
    // ✅ GET APPLICATIONS BY STATUS
=======
    /**
     * Get applications by status for admin
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
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

<<<<<<< HEAD
    // ✅ GET APPLICATION BY ID FOR ADMIN
=======
    /**
     * Get application by ID for admin
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    public AdminApplicationResponseDTO getApplicationByIdForAdmin(Long id) {
        log.info("🔍 Admin: Fetching application with ID: {}", id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with ID: " + id));
        return mapToAdminDTO(application);
    }

<<<<<<< HEAD
    // ✅ REVIEW APPLICATION (Approve/Reject)
=======
    /**
     * Review application (approve/reject)
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    @Transactional
    public AdminApplicationResponseDTO reviewApplication(Long id, String decision, String notes, String reviewer) {
        log.info("📝 Reviewing application ID: {} by: {}", id, reviewer);

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with ID: " + id));

<<<<<<< HEAD
=======
        // Check if application can be reviewed
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
        if (!application.getStatus().isReviewable()) {
            throw new InvalidStatusException(
                    "Application cannot be reviewed. Current status: " + application.getStatus()
            );
        }

<<<<<<< HEAD
=======
        // Validate decision
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
        ApplicationStatus newStatus;
        try {
            newStatus = ApplicationStatus.valueOf(decision.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid decision. Allowed: ADMITTED, REJECTED");
        }

        if (!newStatus.isFinal()) {
            throw new IllegalArgumentException("Decision must be ADMITTED or REJECTED");
        }

<<<<<<< HEAD
=======
        // Update application
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
        application.setStatus(newStatus);
        application.setHrNotes(notes);
        application.setReviewedAt(LocalDateTime.now());
        application.setReviewedBy(reviewer);

        Application updated = applicationRepository.save(application);
        log.info("✅ Application {} reviewed - Decision: {}", application.getTrackingId(), newStatus);

        return mapToAdminDTO(updated);
    }

<<<<<<< HEAD
    // ✅ DELETE APPLICATION
=======
    /**
     * Delete application
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    @Transactional
    public void deleteApplication(Long id) {
        log.info("🗑️ Deleting application with id: {}", id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
        applicationRepository.delete(application);
        log.info("✅ Application deleted successfully!");
    }

<<<<<<< HEAD
    // ✅ GET APPLICATION STATISTICS
=======
    /**
     * Get application statistics for admin dashboard
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
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

<<<<<<< HEAD
=======
    /**
     * Convert Application entity to AdminApplicationResponseDTO
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
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

<<<<<<< HEAD
=======
    /**
     * Convert Application entity to ApplicationResponseDTO
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
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

<<<<<<< HEAD

=======
    /**
     * Generate unique tracking ID
     */
>>>>>>> 8e05a6a8d98d4f2463ae6585d313e11586671536
    private String generateTrackingId() {
        return "APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}