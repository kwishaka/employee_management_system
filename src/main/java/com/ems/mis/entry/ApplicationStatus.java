package com.ems.mis.entry;

public enum ApplicationStatus {
    PENDING("Application is waiting for review"),
    REVIEWED("Application has been reviewed by HR"),
    ADMITTED("Application has been approved"),
    REJECTED("Application has been rejected");

    private final String description;

    ApplicationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
