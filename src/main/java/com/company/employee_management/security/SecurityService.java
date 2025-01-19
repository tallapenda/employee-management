package com.company.employee_management.security;

import com.company.employee_management.model.Employee;
import com.company.employee_management.model.Role;
import com.company.employee_management.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    
    public boolean canAccessEmployee(Employee employee) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        
        // Admins and HR have full access
        if (hasRole(currentUser, Role.ADMIN) || hasRole(currentUser, Role.HR)) {
            return true;
        }
        
        // Managers can only access employees in their department
        if (hasRole(currentUser, Role.MANAGER)) {
            return employee.getDepartment().equals(currentUser.getDepartment());
        }
        
        return false;
    }
    
    public boolean canModifyEmployee(Employee employee) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        
        // Admins and HR can modify any employee
        if (hasRole(currentUser, Role.ADMIN) || hasRole(currentUser, Role.HR)) {
            return true;
        }
        
        // Managers can only modify employees in their department
        if (hasRole(currentUser, Role.MANAGER)) {
            return employee.getDepartment().equals(currentUser.getDepartment());
        }
        
        return false;
    }
    
    public boolean canDeleteEmployee(Employee employee) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        
        // Only Admins and HR can delete employees
        return hasRole(currentUser, Role.ADMIN) || hasRole(currentUser, Role.HR);
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return (User) authentication.getPrincipal();
    }
    
    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
            .anyMatch(role -> role.getName().equals(roleName));
    }
}