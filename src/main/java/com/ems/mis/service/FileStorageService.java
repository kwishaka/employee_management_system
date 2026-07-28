package com.ems.mis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "image/jpeg",
            "image/png"
    );

    public String storeFile(MultipartFile file, String prefix) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Validate file
        validateFile(file);

        // Create upload directory
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String filename = prefix + "_" + UUID.randomUUID().toString() + extension;
        Path filePath = uploadPath.resolve(filename);

        // Save file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        log.info("✅ File stored: {}", filename);

        return "/uploads/" + filename;
    }

    private void validateFile(MultipartFile file) {
        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size exceeds maximum limit of 10MB");
        }

        // Check file type
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new RuntimeException("Invalid file type. Allowed types: PDF, DOC, DOCX, JPG, PNG");
        }
    }
}
