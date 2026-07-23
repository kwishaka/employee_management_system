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

    // ===== FIND BY TRACKING ID (Feature 2) =====
    Optional<Application> findByTrackingId(String trackingId);

    // ===== FIND BY EMAIL (Feature 1) =====
    Optional<Application> findByEmail(String email);

    // ===== CHECK IF EMAIL EXISTS (Feature 1) =====
    boolean existsByEmail(String email);

    // ===== FIND BY STATUS (Feature 3 - Admin) =====
    List<Application> findByStatus(ApplicationStatus status);

    // ===== COUNT BY STATUS (Feature 3 - Admin Dashboard) =====
    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = :status")
    long countByStatus(@Param("status") ApplicationStatus status);
}