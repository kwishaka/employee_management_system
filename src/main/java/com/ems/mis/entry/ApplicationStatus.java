package com.ems.mis.entry;
public enum ApplicationStatus {
    PENDING("Application is pending review"),
    REVIEWED("Application has been reviewed"),
    ADMITTED("Application has been admitted"),
    REJECTED("Application has been rejected");

    private final String description;

    ApplicationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReviewable() {
        return this == PENDING || this == REVIEWED;
    }

    public boolean isFinal() {
        return this == ADMITTED || this == REJECTED;
    }
}
