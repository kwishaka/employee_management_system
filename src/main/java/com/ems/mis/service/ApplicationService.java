package com.ems.mis.service;

import com.ems.mis.dto.ApplicationRequestDTO;
import com.ems.mis.dto.ApplicationResponseDTO;
import com.ems.mis.entry.Application;
import com.ems.mis.entry.ApplicationStatus;
import com.ems.mis.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final FileStorageService fileStorageService;

    /**
     * FEATURE 1: Submit new application with documents
     */
    @Transactional
    public ApplicationResponseDTO submitApplication(
            ApplicationRequestDTO request,
            MultipartFile resume,
            MultipartFile idDocument) throws IOException {

        log.info("📝 Processing application submission for: {}", request.getEmail());

        // 1. Validate duplicate applications
        validateDuplicateApplication(request);

        // 2. Store files
        String resumeUrl = fileStorageService.storeFile(resume, "resume_" + request.getEmail());
        String idDocumentUrl = fileStorageService.storeFile(idDocument, "id_" + request.getEmail());

        // 3. Generate Track ID
        String trackId = generateTrackId();

        // 4. Create application entity
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

        // 5. Save to database
        Application saved = applicationRepository.save(application);

        log.info("✅ Application submitted successfully with Track ID: {}", trackId);

        // 6. Build response
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