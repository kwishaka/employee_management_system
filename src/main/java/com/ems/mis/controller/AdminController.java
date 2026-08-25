package com.ems.mis.controller;
import org.springframework.security.core.Authentication;
import com.ems.mis.dto.AdminApplicationResponseDTO;
import com.ems.mis.dto.ReviewRequestDTO;
import com.ems.mis.service.ApplicationService;
import com.ems.mis.entry.Application;
import com.ems.mis.service.FileStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_ADMIN')")

public class AdminController {
    private final FileStorageService fileStorageService;
    private final ApplicationService applicationService;
    @GetMapping("/applications")
    public ResponseEntity<List<AdminApplicationResponseDTO>> getallApplications(){
        return ResponseEntity.ok(applicationService.getAllApplicationsForAdmin());
    }
    @GetMapping("/applications/status/{status}")
    public ResponseEntity<List<AdminApplicationResponseDTO>> getApplicationsByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(applicationService.getApplicationsByStatusForAdmin(status));
    }
    @GetMapping("/applications/{id}")
    public ResponseEntity<AdminApplicationResponseDTO> getApplicationById(
            @PathVariable Long id) {

        log.info(" Admin: Fetching application ID: {}", id);

        return ResponseEntity.ok(applicationService.getApplicationByIdForAdmin(id));
    }


    @PutMapping("/applications/{id}/review")
    public ResponseEntity<AdminApplicationResponseDTO> reviewApplication(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequestDTO reviewRequest,
            Authentication authentication) {

        String reviewerUsername = authentication.getName();

        log.info(" Admin: Reviewing application ID: {} by: {}", id, reviewerUsername);
        return ResponseEntity.ok(applicationService.reviewApplication(
                id,
                reviewRequest.getDecision(),
                reviewRequest.getNotes(),
                reviewerUsername
        ));
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {

        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getApplicationStats() {
        log.info(" Admin: Fetching application statistics");
        return ResponseEntity.ok(applicationService.getApplicationStats());
    }
    @GetMapping("/applications/{id}/documents/{type}")
    public ResponseEntity<Resource> getDocument(
            @PathVariable Long id,
            @PathVariable String type) throws IOException {

        log.info("Admin: Opening {} document for application ID: {}", type, id);

        Application application = applicationService.getApplicationEntityById(id);

        String fileUrl;

        if ("resume".equalsIgnoreCase(type)) {
            fileUrl = application.getResumeUrl();
        } else if ("idDocument".equalsIgnoreCase(type)) {
            fileUrl = application.getIdDocumentUrl();
        } else {
            return ResponseEntity.badRequest().build();
        }

        if (fileUrl == null || fileUrl.isBlank()) {
            log.warn("No {} document found for application ID: {}", type, id);
            return ResponseEntity.notFound().build();
        }

        Resource resource = fileStorageService.loadFileAsResource(fileUrl);

        String filename = fileUrl.substring(
                fileUrl.lastIndexOf("/") + 1
        );

        // Detect the correct content type
        MediaType mediaType = MediaTypeFactory
                .getMediaType(filename)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        log.info(
                "Serving document: {} with content type: {}",
                filename,
                mediaType
        );

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + filename + "\""
                )
                .body(resource);
    }
}