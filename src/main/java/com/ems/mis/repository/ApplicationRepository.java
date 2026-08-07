package com.ems.mis.repository;


import com.ems.mis.entry.Application;
import com.ems.mis.entry.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

<<<<<<< HEAD

    Optional<Application> findByTrackingId(String trackingId);


=======
    Optional<Application> findByTrackingId(String trackingId);

>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
    Optional<Application> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Application> findByStatus(ApplicationStatus status);

<<<<<<< HEAD

=======
>>>>>>> fe4d0cb76b8b8c84e96964f43d230158edc6d715
    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = :status")
    long countByStatus(@Param("status") ApplicationStatus status);
}