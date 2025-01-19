package com.company.employee_management.service;

import com.company.employee_management.model.Employee;
import com.company.employee_management.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
	 private final EmployeeRepository employeeRepository;
	    private final AuditService auditService;

	    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
	    @Transactional
	    public Employee createEmployee(Employee employee) {
	    	
	        if (employeeRepository.existsById(employee.getEmployeeId())) {
	            throw new IllegalArgumentException("Employee ID already exists");
	        }
	        Employee savedEmployee = employeeRepository.save(employee);
	        auditService.logAction("EMPLOYEE", employee.getEmployeeId(), "CREATE", null, employee.toString());
	        return savedEmployee;
	    }
	    

	    @PreAuthorize("hasAnyRole('HR', 'ADMIN', 'MANAGER')")
	    public Employee getEmployee(String employeeId) {
	        return employeeRepository.findById(employeeId)
	                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
	    }

	    @PreAuthorize("hasAnyRole('HR', 'ADMIN', 'MANAGER')")
	    public Page<Employee> searchEmployees(Pageable pageable) {
	        return employeeRepository.findAll(pageable);
	    }

	    @PreAuthorize("hasAnyRole('HR', 'ADMIN', 'MANAGER')")
	    @Transactional
	    public Employee updateEmployee(String employeeId, Employee employee) {
	        Employee existingEmployee = getEmployee(employeeId);
	        String oldValue = existingEmployee.toString();
	        
	        // Mise à jour infos employer
	        existingEmployee.setFullName(employee.getFullName());
	        existingEmployee.setJobTitle(employee.getJobTitle());
	        existingEmployee.setDepartment(employee.getDepartment());
	        existingEmployee.setEmploymentStatus(employee.getEmploymentStatus());
	        existingEmployee.setEmail(employee.getEmail());
	        existingEmployee.setPhoneNumber(employee.getPhoneNumber());
	        

	        Employee updatedEmployee = employeeRepository.save(existingEmployee);
	        auditService.logAction("EMPLOYEE", employeeId, "UPDATE", oldValue, updatedEmployee.toString());
	        return updatedEmployee;
	    }

	    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
	    @Transactional
	    public void deleteEmployee(String employeeId) {
	        Employee employee = getEmployee(employeeId);
	        employeeRepository.delete(employee);
	        auditService.logAction("EMPLOYEE", employeeId, "DELETE", employee.toString(), null);
	    }
}