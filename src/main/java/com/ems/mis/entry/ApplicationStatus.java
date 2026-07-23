package com.ems.mis.entry;

public enum ApplicationStatus {
    PENDING("Your application is waiting for review by HR"),
    REVIEWED("Your application has been reviewed"),
    ADMITTED("Congratulations! Your application has been approved"),
    REJECTED("We regret to inform you that your application has been rejected");

    private final String description;

    ApplicationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // ✅ Add this method - checks if application can be reviewed
    public boolean isReviewable() {
        return this == PENDING || this == REVIEWED;
    }

    // ✅ Add this method - checks if status is final
    public boolean isFinal() {
        return this == ADMITTED || this == REJECTED;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
