
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

    public boolean isReviewable() {
        return this == PENDING || this == REVIEWED;
    }

    public boolean isFinal() {
        return this == ADMITTED || this == REJECTED;
    }

    @Override
    public String toString() {
        return this.name();
    }
}