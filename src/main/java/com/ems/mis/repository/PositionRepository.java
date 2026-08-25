package com.ems.mis.repository;
import com.ems.mis.entry.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByAvailableTrue();
    Optional<Position> findByTitle(String title);
    boolean existsByTitle(String title);
}