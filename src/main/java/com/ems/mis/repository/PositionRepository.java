package com.ems.mis.repository;

import com.ems.mis.entry.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByTitle(String title);

    List<Position> findByActiveTrue();

    List<Position> findByActiveTrueAndDeadlineAfter(LocalDate date);

    boolean existsByTitle(String title);
}