package com.ems.mis.service;

import com.ems.mis.dto.EmployeeRequestDTO;
import com.ems.mis.dto.EmployeeResponseDTO;
import com.ems.mis.entry.Employee;
import com.ems.mis.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // =========================
    // Create Employee
    // =========================
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Employee email already exists");
        }

        Employee employee = new Employee();

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setDepartment(dto.getDepartment());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());

        Employee savedEmployee = repository.save(employee);

        return mapToResponse(savedEmployee);
    }

    // =========================
    // Get All Employees
    // =========================
    public List<EmployeeResponseDTO> getAllEmployees() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // Get Employee By ID
    // =========================
    public EmployeeResponseDTO getEmployeeById(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return mapToResponse(employee);
    }

    // =========================
    // Update Employee
    // =========================
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Prevent duplicate email
        if (!employee.getEmail().equals(dto.getEmail())
                && repository.existsByEmail(dto.getEmail())) {

            throw new RuntimeException("Employee email already exists");
        }

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setDepartment(dto.getDepartment());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());

        Employee updatedEmployee = repository.save(employee);

        return mapToResponse(updatedEmployee);
    }

    // =========================
    // Delete Employee
    // =========================
    public void deleteEmployee(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        repository.delete(employee);
    }

    // =========================
    // Helper Method
    // =========================
    private EmployeeResponseDTO mapToResponse(Employee employee) {

        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getDepartment(),
                employee.getPosition(),
                employee.getSalary()
        );
    }
}