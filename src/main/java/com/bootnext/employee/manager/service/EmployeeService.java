package com.bootnext.employee.manager.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bootnext.employee.manager.payload.EmployeeDTO;

public interface EmployeeService {

	public List<EmployeeDTO> getAllEmployees();
	public EmployeeDTO getEmployeeById(Long employeeId);
	public EmployeeDTO addEmployee(EmployeeDTO employeeDTO);
	public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);
	public boolean upload(MultipartFile file);
	public EmployeeDTO getEmployeeByEmail(String employeeEmail);
}
