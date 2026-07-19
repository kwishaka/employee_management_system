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

    Optional<Application> findByTrackId(String trackId);

    boolean existsByEmail(String email);
    boolean existsByNationalId(String nationalId);

    Optional<Application> findByEmail(String email);

    List<Application> findByStatus(ApplicationStatus status);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = :status")
    long countByStatus(@Param("status") ApplicationStatus status);
}