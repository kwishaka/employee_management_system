package com.ems.mis.repository;

import com.ems.mis.entry.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByTrackId(String trackId);

    boolean existsByEmail(String email);
    boolean existsByNationalId(String nationalId);

    Optional<Application> findByEmail(String email);
}
