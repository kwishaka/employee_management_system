package com.ems.mis.service;

import com.ems.mis.dto.ApplicationRequestDTO;
import com.ems.mis.dto.ApplicationResponseDTO;
import com.ems.mis.dto.StatusResponseDTO;
import com.ems.mis.entry.Application;
import com.ems.mis.entry.ApplicationStatus;
import com.ems.mis.exception.ApplicationNotFoundException;
import com.ems.mis.repository.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    @Autowired
    private ApplicationRepository applicationRepository;

    public ApplicationResponseDTO createApplication(ApplicationRequestDTO requestDTO) {
        log.info("Creating new application for: {}", requestDTO.getFullName());

        Application application = new Application();
        application.setFullName(requestDTO.getFullName());
        application.setEmail(requestDTO.getEmail());
        application.setPhone(requestDTO.getPhone());
        application.setPosition(requestDTO.getPosition());
        application.setStatus(ApplicationStatus.PENDING);
        application.setAppliedDate(LocalDateTime.now());

        Application saved = applicationRepository.save(application);
        return mapToResponseDTO(saved);
    }

    public ApplicationResponseDTO getApplication(Long id) {
        log.info("Fetching application with id: {}", id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
        return mapToResponseDTO(application);
    }

    public List<ApplicationResponseDTO> getAllApplications() {
        log.info("Fetching all applications");
        return applicationRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public ApplicationResponseDTO updateApplication(Long id, ApplicationRequestDTO requestDTO) {
        log.info("Updating application with id: {}", id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));

        application.setFullName(requestDTO.getFullName());
        application.setEmail(requestDTO.getEmail());
        application.setPhone(requestDTO.getPhone());
        application.setPosition(requestDTO.getPosition());

        Application updated = applicationRepository.save(application);
        return mapToResponseDTO(updated);
    }

    public StatusResponseDTO updateApplicationStatus(Long id, String status) {
        log.info("Updating status for application id: {} to: {}", id, status);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));

        ApplicationStatus newStatus = ApplicationStatus.valueOf(status.toUpperCase());
        application.setStatus(newStatus);

        Application updated = applicationRepository.save(application);

        StatusResponseDTO response = new StatusResponseDTO();
        response.setTrackingId(updated.getTrackingId());
        response.setFullName(updated.getFullName());
        response.setStatus(updated.getStatus().name());
        response.setMessage("Status updated successfully");
        return response;
    }

    public void deleteApplication(Long id) {
        log.info("Deleting application with id: {}", id);
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id: " + id));
        applicationRepository.delete(application);
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
}