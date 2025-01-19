package com.company.employee_management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.employee_management.model.Employee;
import com.company.employee_management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService2 {
    
    private final EmployeeRepository employeeRepository;
    
    @Transactional(readOnly = true)
    public List<Employee> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return List.of();
        }
        return employeeRepository.findByNameContainingIgnoreCase(name.trim());
    }
    
    @Transactional(readOnly = true)
    public Employee searchById(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            return null;
        }
        return employeeRepository.findByEmployeeId(employeeId.trim());
    }
    
    @Transactional(readOnly = true)
    public List<Employee> searchByPhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return List.of();
        }
        return employeeRepository.findByPhoneNumberContaining(phoneNumber.trim());
    }
    
    @Transactional(readOnly = true)
    public List<Employee> searchByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            return List.of();
        }
        return employeeRepository.findByDepartmentContainingIgnoreCase(department.trim());
    }
    
    @Transactional(readOnly = true)
    public List<Employee> searchByJobTitle(String jobTitle) {
        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            return List.of();
        }
        return employeeRepository.findByJobTitleContainingIgnoreCase(jobTitle.trim());
    }
    
    @Transactional(readOnly = true)
    public List<Employee> searchWithFilters(String status, String department, 
                                          LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findWithFilters(status, department, startDate, endDate);
    }

	public void delete(Long id) {
		
		employeeRepository.deleteById(id);
		
	}

	public void save(Employee employee) {
		employeeRepository.save(employee);
		
	}
}
