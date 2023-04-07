package com.bootnext.employee.manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootnext.employee.manager.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	

		Optional<Employee> findByEmployeeEmail(String employeeEmail);
}
