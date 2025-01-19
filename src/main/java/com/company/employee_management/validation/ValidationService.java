package com.company.employee_management.validation;

import com.company.employee_management.model.Employee;
import com.company.employee_management.repository.EmployeeRepository;

import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValidationService {
    
    private final EmployeeRepository employeeRepository;
    private final Validator validator;
    
    public void validateEmployee(Employee employee) {
        // Perform Jakarta Validation
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        
        // Check for duplicate employee ID
        if (employee.getId() == null) { // New employee
            if (employeeRepository.findByEmployeeId(employee.getEmployeeId()) != null) {
                throw new ValidationException("Employee ID already exists");
            }
        } else { // Existing employee
            Employee existing = employeeRepository.findByEmployeeId(employee.getEmployeeId());
            if (existing != null && !existing.getId().equals(employee.getId())) {
                throw new ValidationException("Employee ID already exists");
            }
        }
        
        // Check for duplicate email
        if (employeeRepository.existsByEmailAndIdNot(
                employee.getEmail(), 
                employee.getId() != null ? employee.getId() : -1L)) {
            throw new ValidationException("Email already exists");
        }
        
        // Check for duplicate national ID if provided
        if (employee.getNationalId() != null && !employee.getNationalId().isEmpty()) {
            if (employeeRepository.existsByNationalIdAndIdNot(
                    employee.getNationalId(), 
                    employee.getId() != null ? employee.getId() : -1L)) {
                throw new ValidationException("National ID already exists");
            }
        }
    }
    
    public String formatValidationErrors(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
            .collect(Collectors.joining("\n"));
    }
}