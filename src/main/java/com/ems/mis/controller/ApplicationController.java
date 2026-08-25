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
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApplicationResponseDTO> submitApplication(
            @Valid @RequestPart("application") ApplicationRequestDTO request,
            @RequestPart(value = "resume" ,required = true) MultipartFile resume,
            @RequestPart(value = "idDocument",required = true) MultipartFile idDocument) throws IOException {

        log.info("Received application submission from: {}", request.getEmail());
        ApplicationResponseDTO response = applicationService.submitApplication(request, resume, idDocument);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> createApplication(
            @Valid @RequestBody ApplicationRequestDTO request) throws IOException {

        log.info("Received JSON application submission from: {}", request.getEmail());
        ApplicationResponseDTO response = applicationService.createApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/track/{trackingId}")
    public ResponseEntity<StatusResponseDTO> trackApplication(@PathVariable String trackingId) {
        log.info("Tracking application: {}", trackingId);
        StatusResponseDTO response = applicationService.getApplicationStatus(trackingId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getApplication(@PathVariable Long id) {
        log.info("Fetching application with ID: {}", id);
        ApplicationResponseDTO response = applicationService.getApplication(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> getAllApplications() {
        log.info("Fetching all applications");
        List<ApplicationResponseDTO> responses = applicationService.getAllApplications();
        return ResponseEntity.ok(responses);
    }
}


