package com.company.employee_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Employee ID is required")
    @Pattern(regexp = "^EMP\\d{4}$", message = "Employee ID must be in format EMP followed by 4 digits")
    private String employeeId;
    
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Full name contains invalid characters")
    private String fullName;
    
    @NotBlank(message = "Department is required")
    @Size(max = 50, message = "Department name cannot exceed 50 characters")
    private String department;
    
    @NotBlank(message = "Job title is required")
    @Size(max = 100, message = "Job title cannot exceed 100 characters")
    private String jobTitle;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phoneNumber;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;
    
    @NotNull(message = "Hire date is required")
    @Past(message = "Hire date must be in the past")
    private LocalDate hireDate;
    
    @NotNull(message = "Employment status is required")
    @Pattern(regexp = "^(Active|Inactive|On Leave)$", message = "Invalid employment status")
    private String employmentStatus;
    
    @Min(value = 0, message = "Salary cannot be negative")
    private Double salary;
    
    @Pattern(regexp = "^\\d{10}$", message = "Invalid national ID format")
    private String nationalId;
    
    @PrePersist
    @PreUpdate
    private void validateEmployee() {
        if (fullName != null) {
            fullName = fullName.trim();
        }
        if (email != null) {
            email = email.toLowerCase().trim();
        }
    }
}