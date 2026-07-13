package com.ems.mis.controller;

import com.ems.mis.dto.EmployeeRequestDTO;
import com.ems.mis.dto.EmployeeResponseDTO;
import com.ems.mis.entry.Employee;
import com.ems.mis.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // Create Employee
    @PostMapping
    public EmployeeResponseDTO createEmployee(
            @Valid @RequestBody EmployeeRequestDTO dto) {

        return service.createEmployee(dto);
    }

    // Get All Employees
    @GetMapping
    public List<EmployeeResponseDTO> getAllEmployees() {
        return service.getAllEmployees();
    }

    // Get Employee By ID
    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployeeById(
            @PathVariable("id") Long id) {

        return service.getEmployeeById(id);
    }
    @PutMapping("/{id}")
    public EmployeeResponseDTO updateEmployee(
            @PathVariable("id") Long id,
            @Valid @RequestBody EmployeeRequestDTO dto) {

        return service.updateEmployee(id, dto);
    }
    @DeleteMapping("/{id}")
    public String deleteEmployee(
            @PathVariable("id") Long id) {

        service.deleteEmployee(id);
        return "Employee deleted successfully";
    }
}
