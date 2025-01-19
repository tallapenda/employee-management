package com.company.employee_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.company.employee_management.model.Employee;
import com.company.employee_management.repository.EmployeeRepository;
import com.company.employee_management.service.EmployeeService2;




@ExtendWith(MockitoExtension.class)
class EmployeeManagementApplicationTests {

	 @Mock
	    private EmployeeRepository employeeRepository;

	    @InjectMocks
	    private EmployeeService2 employeeService;

	    private Employee testEmployee;

	    @BeforeEach
	    void setUp() {
	        testEmployee = new Employee();
	        testEmployee.setEmployeeId("EMP001");
	        testEmployee.setFullName("John Doe");
	        testEmployee.setPhoneNumber("1234567890");
	        testEmployee.setDepartment("IT");
	        testEmployee.setJobTitle("Software Engineer");
	        testEmployee.setEmploymentStatus("Active");
	        testEmployee.setHireDate(LocalDate.now());
	    }

	    @Test
	    void searchByName_ValidName_ReturnsEmployeeList() {
	        when(employeeRepository.findByNameContainingIgnoreCase("John"))
	            .thenReturn(List.of(testEmployee));

	        List<Employee> result = employeeService.searchByName("John");

	        assertNotNull(result);
	        assertEquals(1, result.size());
	        assertEquals("John Doe", result.get(0).getFullName());
	        verify(employeeRepository).findByNameContainingIgnoreCase("John");
	    }

	    @Test
	    void searchById_ValidId_ReturnsEmployee() {
	        when(employeeRepository.findByEmployeeId("EMP001"))
	            .thenReturn(testEmployee);

	        Employee result = employeeService.searchById("EMP001");

	        assertNotNull(result);
	        assertEquals("EMP001", result.getEmployeeId());
	        verify(employeeRepository).findByEmployeeId("EMP001");
	    }

	    @Test
	    void searchByPhone_ValidPhone_ReturnsEmployeeList() {
	        when(employeeRepository.findByPhoneNumberContaining("123"))
	            .thenReturn(List.of(testEmployee));

	        List<Employee> result = employeeService.searchByPhone("123");

	        assertNotNull(result);
	        assertEquals(1, result.size());
	        assertEquals("1234567890", result.get(0).getPhoneNumber());
	        verify(employeeRepository).findByPhoneNumberContaining("123");
	    }
	    
	    @Test
	    void searchByDepartment_ValidDepartment_ReturnsEmployeeList() {
	        when(employeeRepository.findByDepartmentContainingIgnoreCase("IT"))
	            .thenReturn(List.of(testEmployee));

	        List<Employee> result = employeeService.searchByDepartment("IT");

	        assertNotNull(result);
	        assertEquals(1, result.size());
	        assertEquals("IT", result.get(0).getDepartment());
	        verify(employeeRepository).findByDepartmentContainingIgnoreCase("IT");
	    }
	    
	    @Test
	    void searchByJobTitle_ValidJobTitle_ReturnsEmployeeList() {
	        when(employeeRepository.findByJobTitleContainingIgnoreCase("Engineer"))
	            .thenReturn(List.of(testEmployee));

	        List<Employee> result = employeeService.searchByJobTitle("Engineer");

	        assertNotNull(result);
	        assertEquals(1, result.size());
	        assertEquals("Software Engineer", result.get(0).getJobTitle());
	        verify(employeeRepository).findByJobTitleContainingIgnoreCase("Engineer");
	    }
	    
	    @Test
	    void searchWithFilters_ValidFilters_ReturnsFilteredList() {
	        LocalDate startDate = LocalDate.now().minusMonths(1);
	        LocalDate endDate = LocalDate.now();
	        
	        when(employeeRepository.findWithFilters("Active", "IT", startDate, endDate))
	            .thenReturn(List.of(testEmployee));

	        List<Employee> result = employeeService.searchWithFilters(
	            "Active", "IT", startDate, endDate);

	        assertNotNull(result);
	        assertEquals(1, result.size());
	        assertEquals("Active", result.get(0).getEmploymentStatus());
	        assertEquals("IT", result.get(0).getDepartment());
	        verify(employeeRepository).findWithFilters("Active", "IT", startDate, endDate);
	    }

	    @Test
	    void searchByName_EmptyInput_ReturnsEmptyList() {
	        List<Employee> result = employeeService.searchByName("");
	        assertTrue(result.isEmpty());
	        verifyNoInteractions(employeeRepository);
	    }

	    @Test
	    void searchById_EmptyInput_ReturnsNull() {
	        Employee result = employeeService.searchById("");
	        assertNull(result);
	        verifyNoInteractions(employeeRepository);
	    }

	    @Test
	    void searchByPhone_EmptyInput_ReturnsEmptyList() {
	        List<Employee> result = employeeService.searchByPhone("");
	        assertTrue(result.isEmpty());
	        verifyNoInteractions(employeeRepository);
	    }
	    
	    @Test
	    void searchByDepartment_EmptyInput_ReturnsEmptyList() {
	        List<Employee> result = employeeService.searchByDepartment("");
	        assertTrue(result.isEmpty());
	        verifyNoInteractions(employeeRepository);
	    }
	    
	    @Test
	    void searchByJobTitle_EmptyInput_ReturnsEmptyList() {
	        List<Employee> result = employeeService.searchByJobTitle("");
	        assertTrue(result.isEmpty());
	        verifyNoInteractions(employeeRepository);
	    }

}
