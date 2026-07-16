package com.ems.mis.dto;

import java.time.LocalDateTime;

public class StatusResponseDTO {
    private String trackId;
    private String fullName;
    private String email;
    private String status;
    private String statusDescription;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
    private String hrNotes;
    private String message;

    // Default constructor
    public StatusResponseDTO() {}

    // All-args constructor
    public StatusResponseDTO(String trackId, String fullName, String email, String status,
                             String statusDescription, LocalDateTime submittedAt, LocalDateTime reviewedAt,
                             String hrNotes, String message) {
        this.trackId = trackId;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.statusDescription = statusDescription;
        this.submittedAt = submittedAt;
        this.reviewedAt = reviewedAt;
        this.hrNotes = hrNotes;
        this.message = message;
    }

    // Getters and Setters
    public String getTrackId() { return trackId; }
    public void setTrackId(String trackId) { this.trackId = trackId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStatusDescription() { return statusDescription; }
    public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }

    public String getHrNotes() { return hrNotes; }
    public void setHrNotes(String hrNotes) { this.hrNotes = hrNotes; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    // Builder pattern
    public static StatusResponseDTOBuilder builder() {
        return new StatusResponseDTOBuilder();
    }

    public static class StatusResponseDTOBuilder {
        private String trackId;
        private String fullName;
        private String email;
        private String status;
        private String statusDescription;
        private LocalDateTime submittedAt;
        private LocalDateTime reviewedAt;
        private String hrNotes;
        private String message;

        public StatusResponseDTOBuilder trackId(String trackId) { this.trackId = trackId; return this; }
        public StatusResponseDTOBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public StatusResponseDTOBuilder email(String email) { this.email = email; return this; }
        public StatusResponseDTOBuilder status(String status) { this.status = status; return this; }
        public StatusResponseDTOBuilder statusDescription(String statusDescription) { this.statusDescription = statusDescription; return this; }
        public StatusResponseDTOBuilder submittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; return this; }
        public StatusResponseDTOBuilder reviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; return this; }
        public StatusResponseDTOBuilder hrNotes(String hrNotes) { this.hrNotes = hrNotes; return this; }
        public StatusResponseDTOBuilder message(String message) { this.message = message; return this; }

        public StatusResponseDTO build() {
            return new StatusResponseDTO(trackId, fullName, email, status, statusDescription, submittedAt, reviewedAt, hrNotes, message);
        }
    }
}