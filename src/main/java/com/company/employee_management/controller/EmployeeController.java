package com.company.employee_management.controller;

import com.company.employee_management.model.Employee;
import com.company.employee_management.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Create a new employee")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<Employee> getEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    @GetMapping
    @Operation(summary = "Search employees with pagination")
    public ResponseEntity<Page<Employee>> searchEmployees(Pageable pageable) {
        return ResponseEntity.ok(employeeService.searchEmployees(pageable));
    }

    @PutMapping("/{employeeId}")
    @Operation(summary = "Update an employee")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable String employeeId,
            @Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employee));
    }

    @DeleteMapping("/{employeeId}")
    @Operation(summary = "Delete an employee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}