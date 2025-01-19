package com.company.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.company.employee_management.model.AuditLog;


public interface AuditLogRepository  extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog>{

}
