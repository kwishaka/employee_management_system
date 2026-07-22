package com.ems.mis.entry;

public enum ApplicationStatus {
    PENDING("Application is pending review"),
    REVIEWED("Application has been reviewed"),
    ADMITTED("Application has been admitted"),
    REJECTED("Application has been rejected");

    private final String description;

    // Business logic: An application can only be reviewed if it's PENDING

    ApplicationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReviewable() {
        return this == PENDING || this == REVIEWED;
    }

    //  WHY WE NEED THIS:
    // Business logic: Once final, status cannot change
    public boolean isFinal() {
        return this == ADMITTED || this == REJECTED;
    }
}
