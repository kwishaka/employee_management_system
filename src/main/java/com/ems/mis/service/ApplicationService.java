package com.ems.mis.service;

import com.ems.mis.dto.ApplicationRequestDTO;
import com.ems.mis.dto.ApplicationResponseDTO;
import com.ems.mis.dto.StatusResponseDTO;
import com.ems.mis.entry.Application;
import com.ems.mis.entry.ApplicationStatus;
import com.ems.mis.exception.ApplicationNotFoundException;
import com.ems.mis.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private final FileStorageService fileStorageService;  // ✅ ADDED THIS

    // ========================================
    // FEATURE 1: Submit Application
    // ========================================

    @Transactional
    public ApplicationResponseDTO submitApplication(
            ApplicationRequestDTO request,
            MultipartFile resume,
            MultipartFile idDocument) throws IOException {

        log.info("📝 Processing application submission for: {}", request.getEmail());

        // Validate duplicate
        validateDuplicateApplication(request);

        // Store files using FileStorageService
        String resumeUrl = null;
        String idDocumentUrl = null;

        if (resume != null && !resume.isEmpty()) {
            resumeUrl = fileStorageService.storeFile(resume, "resume_" + request.getEmail());
            log.info("✅ Resume stored: {}", resumeUrl);
        } else {
            log.warn("⚠️ No resume file provided");
        }

        if (idDocument != null && !idDocument.isEmpty()) {
            idDocumentUrl = fileStorageService.storeFile(idDocument, "id_" + request.getEmail());
            log.info("✅ ID Document stored: {}", idDocumentUrl);
        } else {
            log.warn("⚠️ No ID document provided");
        }

        // Generate Track ID
        String trackId = generateTrackId();

        // Create application
        Application application = Application.builder()
                .trackId(trackId)
                .fullName(request.getFullName())
                .email(request.getEmail())
                .nationalId(request.getNationalId())
                .phone(request.getPhone())
                .education(request.getEducation())
                .workExperience(request.getWorkExperience())
                .resumeUrl(resumeUrl)
                .idDocumentUrl(idDocumentUrl)
                .status(ApplicationStatus.PENDING)
                .submittedAt(LocalDateTime.now())
                .build();

        Application saved = applicationRepository.save(application);

        log.info("✅ Application submitted successfully with Track ID: {}", trackId);

        return ApplicationResponseDTO.builder()
                .id(saved.getId())
                .trackId(saved.getTrackId())
                .fullName(saved.getFullName())
                .email(saved.getEmail())
                .status(saved.getStatus().name())
                .submittedAt(saved.getSubmittedAt())
                .resumeUrl(saved.getResumeUrl())
                .idDocumentUrl(saved.getIdDocumentUrl())
                .message("Application submitted successfully! Your Track ID is: " + trackId)
                .build();
    }

    // ========================================
    // FEATURE 2: Track Application Status
    // ========================================

    public StatusResponseDTO getApplicationStatus(String trackId) {
        log.info("🔍 Checking status for Track ID: {}", trackId);

        // Validate Track ID format
        if (!trackId.matches("^APP-[A-Z0-9]{8}$")) {
            throw new ApplicationNotFoundException(
                    trackId + " (Invalid format. Track ID should be like APP-XXXXXXXX)"
            );
        }

        Application application = applicationRepository.findByTrackId(trackId)
                .orElseThrow(() -> new ApplicationNotFoundException(trackId));

        log.info("✅ Status retrieved for Track ID: {} - Status: {}", trackId, application.getStatus());

        return StatusResponseDTO.builder()
                .trackId(application.getTrackId())
                .fullName(application.getFullName())
                .email(application.getEmail())
                .status(application.getStatus().name())
                .statusDescription(application.getStatus().getDescription())
                .submittedAt(application.getSubmittedAt())
                .reviewedAt(application.getReviewedAt())
                .hrNotes(application.getHrNotes())
                .message("Application status retrieved successfully")
                .build();
    }

    // ========================================
    // FEATURE 3: HR Admin Functions
    // ========================================

    public List<ApplicationResponseDTO> getAllApplications() {
        log.info("📋 Fetching all applications for admin");

        return applicationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ApplicationResponseDTO> getApplicationsByStatus(String status) {
        log.info("📋 Fetching applications with status: {}", status);

        ApplicationStatus applicationStatus;
        try {
            applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status. Allowed: PENDING, REVIEWED, ADMITTED, REJECTED");
        }

        return applicationRepository.findByStatus(applicationStatus)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ApplicationResponseDTO getApplicationById(Long id) {
        log.info("🔍 Fetching application with ID: {}", id);

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));

        return convertToDTO(application);
    }

    @Transactional
    public ApplicationResponseDTO reviewApplication(Long id, String decision, String notes, String reviewer) {
        log.info("📝 Reviewing application ID: {} by: {}", id, reviewer);

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));

        if (!application.getStatus().isReviewable()) {
            throw new RuntimeException("Application cannot be reviewed. Current status: " + application.getStatus());
        }

        ApplicationStatus newStatus;
        try {
            newStatus = ApplicationStatus.valueOf(decision.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid decision. Allowed: ADMITTED, REJECTED");
        }

        if (!newStatus.isFinal()) {
            throw new RuntimeException("Decision must be ADMITTED or REJECTED");
        }

        application.setStatus(newStatus);
        application.setHrNotes(notes);
        application.setReviewedAt(LocalDateTime.now());
        application.setReviewedBy(reviewer);

        Application updated = applicationRepository.save(application);

        log.info("✅ Application {} reviewed - Decision: {}", application.getTrackId(), newStatus);

        return convertToDTO(updated);
    }

    @Transactional
    public void deleteApplication(Long id) {
        log.info("🗑️ Deleting application with ID: {}", id);

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));

        // Delete files if they exist
        deleteFile(application.getResumeUrl());
        deleteFile(application.getIdDocumentUrl());

        applicationRepository.delete(application);
        log.info("✅ Application deleted successfully: {}", application.getTrackId());
    }

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
    // Helper Methods
    // ========================================

    private ApplicationResponseDTO convertToDTO(Application application) {
        return ApplicationResponseDTO.builder()
                .id(application.getId())
                .trackId(application.getTrackId())
                .fullName(application.getFullName())
                .email(application.getEmail())
                .status(application.getStatus().name())
                .submittedAt(application.getSubmittedAt())
                .resumeUrl(application.getResumeUrl())
                .idDocumentUrl(application.getIdDocumentUrl())
                .message("Application retrieved successfully")
                .build();
    }

    private void deleteFile(String fileUrl) {
        try {
            if (fileUrl != null && !fileUrl.isEmpty()) {
                String filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                Path filePath = Paths.get("./uploads").resolve(filename);
                Files.deleteIfExists(filePath);
                log.info("🗑️ Deleted file: {}", filename);
            }
        } catch (IOException e) {
            log.warn("⚠️ Failed to delete file: {}", e.getMessage());
        }
    }

    private void validateDuplicateApplication(ApplicationRequestDTO request) {
        if (applicationRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered. Please use a different email.");
        }
        if (applicationRepository.existsByNationalId(request.getNationalId())) {
            throw new RuntimeException("National ID already registered. Please use a different ID.");
        }
    }

    private String generateTrackId() {
        return "APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}