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
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    /**
     * FEATURE 1: Submit new application with documents (JSON + Base64)
     */
    @Transactional
    public ApplicationResponseDTO submitApplication(ApplicationRequestDTO request) throws IOException {

        log.info("📝 Processing application submission for: {}", request.getEmail());

        validateDuplicateApplication(request);

        String resumeUrl = null;
        String idDocumentUrl = null;

        if (request.getResumeBase64() != null && !request.getResumeBase64().isEmpty()) {
            resumeUrl = saveBase64File(
                    request.getResumeBase64(),
                    request.getResumeFileName(),
                    "resume_" + request.getEmail()
            );
        }

        if (request.getIdDocumentBase64() != null && !request.getIdDocumentBase64().isEmpty()) {
            idDocumentUrl = saveBase64File(
                    request.getIdDocumentBase64(),
                    request.getIdDocumentFileName(),
                    "id_" + request.getEmail()
            );
        }

        String trackId = generateTrackId();

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

    /**
     * FEATURE 2: Get application status by Track ID
     */
    public StatusResponseDTO getApplicationStatus(String trackId) {

        Application application = applicationRepository.findByTrackId(trackId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found for Track ID: " + trackId));

        log.info("✅ Status retrieved for Track ID: {}", trackId);

        return StatusResponseDTO.builder()
                .trackId(application.getTrackId())
                .fullName(application.getFullName())
                .email(application.getEmail())
                .status(application.getStatus().name())
                .statusDescription(application.getStatus().toString())
                .submittedAt(application.getSubmittedAt())
                .reviewedAt(application.getReviewedAt())
                .hrNotes(application.getHrNotes())
                .message("Application status retrieved successfully")
                .build();
    }

    private String saveBase64File(String base64Data, String fileName, String prefix) throws IOException {
        if (base64Data == null || base64Data.isEmpty()) {
            return null;
        }

        byte[] fileBytes = Base64.getDecoder().decode(base64Data);

        String uploadDir = "./uploads";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = "";
        if (fileName != null && fileName.contains(".")) {
            extension = fileName.substring(fileName.lastIndexOf("."));
        }
        String filename = prefix + "_" + UUID.randomUUID().toString() + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.write(filePath, fileBytes);

        log.info("✅ File stored: {}", filename);

        return "/uploads/" + filename;
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

    public static Logger getLog() {
        return log;
    }

    
}