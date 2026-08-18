package com.ems.mis.service;

import com.ems.mis.entry.Position;
import com.ems.mis.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    // Get all available positions for applicants
    public List<Position> getAvailablePositions() {
        return positionRepository.findByAvailableTrue();
    }

    // Get all positions for HR
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    // Create a new position
    public Position createPosition(Position position) {

        if (positionRepository.existsByTitle(position.getTitle())) {
            throw new RuntimeException("Position already exists");
        }

        position.setAvailable(true);

        return positionRepository.save(position);
    }

    // Get position by ID
    public Position getPositionById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Position not found"));
    }

    // Update position
    public Position updatePosition(Long id, Position updatedPosition) {

        Position position = getPositionById(id);

        position.setTitle(updatedPosition.getTitle());
        position.setDepartment(updatedPosition.getDepartment());
        position.setDescription(updatedPosition.getDescription());
        position.setAvailable(updatedPosition.isAvailable());

        return positionRepository.save(position);
    }

    // Delete position
    public void deletePosition(Long id) {

        Position position = getPositionById(id);

        positionRepository.delete(position);
    }
}
