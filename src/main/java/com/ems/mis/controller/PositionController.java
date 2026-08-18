package com.ems.mis.controller;
import com.ems.mis.entry.Position;
import com.ems.mis.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    // =========================================================
    // PUBLIC ENDPOINTS - Applicants
    // =========================================================

    // Get positions that are currently available
    @GetMapping
    public ResponseEntity<List<Position>> getAvailablePositions() {

        return ResponseEntity.ok(
                positionService.getAvailablePositions()
        );
    }

    // Get one available position
    @GetMapping("/{id}")
    public ResponseEntity<Position> getPosition(@PathVariable Long id) {

        Position position = positionService.getPositionById(id);

        if (!position.isAvailable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(position);
    }


    // =========================================================
    // HR ADMIN ENDPOINTS
    // =========================================================

    // HR creates a new position
    @PostMapping("/admin")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<Position> createPosition(
            @RequestBody Position position) {

        Position created = positionService.createPosition(position);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // HR views all positions, including unavailable ones
    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<List<Position>> getAllPositions() {

        return ResponseEntity.ok(
                positionService.getAllPositions()
        );
    }

    // HR updates a position
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<Position> updatePosition(
            @PathVariable Long id,
            @RequestBody Position position) {

        Position updated =
                positionService.updatePosition(id, position);

        return ResponseEntity.ok(updated);
    }

    // HR deletes a position
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<Void> deletePosition(
            @PathVariable Long id) {

        positionService.deletePosition(id);

        return ResponseEntity.noContent().build();
    }
}