package com.ems.mis.entry;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "track_id", nullable = false, unique = true, length = 50)
    private String trackId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "national_id", nullable = false, unique = true, length = 20)
    private String nationalId;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String education;

    @Column(name = "work_experience", columnDefinition = "TEXT")
    private String workExperience;

    @Column(name = "resume_url", length = 500)
    private String resumeUrl;

    @Column(name = "id_document_url", length = 500)
    private String idDocumentUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "hr_notes", columnDefinition = "TEXT")
    private String hrNotes;

    @CreationTimestamp
    @Column(name = "submitted_at", updatable = false)
    private LocalDateTime submittedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "reviewed_by", length = 100)
    private String reviewedBy;

    // ===== Default Constructor =====
    public Application() {}

    // ===== All-args Constructor =====
    public Application(Long id, String trackId, String fullName, String email, String nationalId,
                       String phone, String education, String workExperience, String resumeUrl,
                       String idDocumentUrl, ApplicationStatus status, String hrNotes,
                       LocalDateTime submittedAt, LocalDateTime reviewedAt, String reviewedBy) {
        this.id = id;
        this.trackId = trackId;
        this.fullName = fullName;
        this.email = email;
        this.nationalId = nationalId;
        this.phone = phone;
        this.education = education;
        this.workExperience = workExperience;
        this.resumeUrl = resumeUrl;
        this.idDocumentUrl = idDocumentUrl;
        this.status = status;
        this.hrNotes = hrNotes;
        this.submittedAt = submittedAt;
        this.reviewedAt = reviewedAt;
        this.reviewedBy = reviewedBy;
    }

    // ===== Getters and Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTrackId() { return trackId; }
    public void setTrackId(String trackId) { this.trackId = trackId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getWorkExperience() { return workExperience; }
    public void setWorkExperience(String workExperience) { this.workExperience = workExperience; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public String getIdDocumentUrl() { return idDocumentUrl; }
    public void setIdDocumentUrl(String idDocumentUrl) { this.idDocumentUrl = idDocumentUrl; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public String getHrNotes() { return hrNotes; }
    public void setHrNotes(String hrNotes) { this.hrNotes = hrNotes; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }

    public String getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(String reviewedBy) { this.reviewedBy = reviewedBy; }

    // ===== Builder Pattern (Manual) =====
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String trackId;
        private String fullName;
        private String email;
        private String nationalId;
        private String phone;
        private String education;
        private String workExperience;
        private String resumeUrl;
        private String idDocumentUrl;
        private ApplicationStatus status;
        private String hrNotes;
        private LocalDateTime submittedAt;
        private LocalDateTime reviewedAt;
        private String reviewedBy;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder trackId(String trackId) { this.trackId = trackId; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder nationalId(String nationalId) { this.nationalId = nationalId; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder education(String education) { this.education = education; return this; }
        public Builder workExperience(String workExperience) { this.workExperience = workExperience; return this; }
        public Builder resumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; return this; }
        public Builder idDocumentUrl(String idDocumentUrl) { this.idDocumentUrl = idDocumentUrl; return this; }
        public Builder status(ApplicationStatus status) { this.status = status; return this; }
        public Builder hrNotes(String hrNotes) { this.hrNotes = hrNotes; return this; }
        public Builder submittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; return this; }
        public Builder reviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; return this; }
        public Builder reviewedBy(String reviewedBy) { this.reviewedBy = reviewedBy; return this; }

        public Application build() {
            return new Application(id, trackId, fullName, email, nationalId, phone, education,
                    workExperience, resumeUrl, idDocumentUrl, status, hrNotes,
                    submittedAt, reviewedAt, reviewedBy);
        }
    }
}

