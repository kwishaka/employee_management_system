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

    // Create Employee
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

        return new EmployeeResponseDTO(
                savedEmployee.getId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getEmail(),
                savedEmployee.getPhoneNumber(),
                savedEmployee.getDepartment(),
                savedEmployee.getPosition(),
                savedEmployee.getSalary()
        );
    }

    // Get All Employees
    public List<EmployeeResponseDTO> getAllEmployees() {

        return repository.findAll()
                .stream()
                .map(employee -> new EmployeeResponseDTO(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail(),
                        employee.getPhoneNumber(),
                        employee.getDepartment(),
                        employee.getPosition(),
                        employee.getSalary()
                ))
                .collect(Collectors.toList());
    }

    // Get Employee By ID
    public EmployeeResponseDTO getEmployeeById(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

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
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setPosition(updatedEmployee.getPosition());
        employee.setSalary(updatedEmployee.getSalary());

        return repository.save(employee);
    }
}
