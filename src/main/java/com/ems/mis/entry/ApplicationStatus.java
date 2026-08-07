
package com.ems.mis.entry;
public enum ApplicationStatus {
<<<<<<< HEAD
    PENDING("application is waiting for review by HR"),
    REVIEWED("Your application has been reviewed"),
    ADMITTED(" Your application has been approved"),
    REJECTED(" your application has been rejected");

=======
   PENDING("Application is pending review"),
   REVIEWED("Application has been reviewed"),
   ADMITTED("Application has been admitted"),
   REJECTED("Application has been rejected");
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
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
