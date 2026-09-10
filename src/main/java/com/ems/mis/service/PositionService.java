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
    public List<Position> getAvailablePositions() {
        return positionRepository.findByActiveTrueAndDeadlineAfter(java.time.LocalDate.now());
    }
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }
    public Position createPosition(Position position) {
        if (positionRepository.existsByTitle(position.getTitle())) {
            throw new RuntimeException("Position already exists");
        }
        position.setActive(true);
        return positionRepository.save(position);
    }
    public Position getPositionById(Long id) {
        return positionRepository.findById(id)  // 
                .orElseThrow(() -> new RuntimeException("Position not found with ID: " + id));
    }
    public Position updatePosition(Long id, Position updatedPosition) {
        Position position = getPositionById(id);
        position.setTitle(updatedPosition.getTitle());
        position.setDepartment(updatedPosition.getDepartment());
        position.setDescription(updatedPosition.getDescription());
        position.setActive(updatedPosition.getActive());
        position.setDeadline(updatedPosition.getDeadline());
        return positionRepository.save(position);
    }
    public void deletePosition(Long id) {
        Position position = getPositionById(id);
        positionRepository.delete(position);
    }
}




