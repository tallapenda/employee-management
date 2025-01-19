package com.company.employee_management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    public static final String HR = "HR_PERSONNEL";
    public static final String MANAGER = "MANAGER";
    public static final String ADMIN = "ADMINISTRATOR";
}