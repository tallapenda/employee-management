package com.company.employee_management.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.employee_management.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {
	// Search by name (case insensitive, partial match)
    @Query("SELECT e FROM Employee e WHERE LOWER(e.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Search by employee ID (exact match)
    Employee findByEmployeeId(String employeeId);
    
    // Search by phone number (partial match)
    @Query("SELECT e FROM Employee e WHERE e.phoneNumber LIKE CONCAT('%', :phoneNumber, '%')")
    List<Employee> findByPhoneNumberContaining(@Param("phoneNumber") String phoneNumber);
    
    // Search by department (case insensitive, partial match)
    @Query("SELECT e FROM Employee e WHERE LOWER(e.department) LIKE LOWER(CONCAT('%', :department, '%'))")
    List<Employee> findByDepartmentContainingIgnoreCase(@Param("department") String department);
    
    // Search by job title (case insensitive, partial match)
    @Query("SELECT e FROM Employee e WHERE LOWER(e.jobTitle) LIKE LOWER(CONCAT('%', :jobTitle, '%'))")
    List<Employee> findByJobTitleContainingIgnoreCase(@Param("jobTitle") String jobTitle);
    
    // Combined filter query
    @Query("SELECT e FROM Employee e WHERE " +
           "(:status IS NULL OR e.employmentStatus = :status) AND " +
           "(:department IS NULL OR LOWER(e.department) = LOWER(:department)) AND " +
           "(:startDate IS NULL OR e.hireDate >= :startDate) AND " +
           "(:endDate IS NULL OR e.hireDate <= :endDate)")
    List<Employee> findWithFilters(
        @Param("status") String status,
        @Param("department") String department,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    // Check for duplicate email
    boolean existsByEmailAndIdNot(String email, Long id);
    
    // Check for duplicate national ID
    boolean existsByNationalIdAndIdNot(String nationalId, Long id);

	void deleteById(Long id);
}