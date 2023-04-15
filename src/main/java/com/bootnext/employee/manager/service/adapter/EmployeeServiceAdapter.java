package com.bootnext.employee.manager.service.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bootnext.employee.manager.exception.ResourceNotFoundException;
import com.bootnext.employee.manager.model.Employee;
import com.bootnext.employee.manager.payload.EmployeeDTO;
import com.bootnext.employee.manager.repository.EmployeeRepository;
import com.bootnext.employee.manager.service.EmployeeService;
import com.bootnext.employee.manager.utility.FileUpload;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class EmployeeServiceAdapter implements EmployeeService {

	private EmployeeRepository employeeRepository;
	private ModelMapper modelMapper;
	private FileUpload fileUpload;
	@Override
	public List<EmployeeDTO> getAllEmployees() {

		List<Employee> findAll = this.employeeRepository.findAll();
		return findAll.stream().map(employee->this.modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
	}
	@Override
	public EmployeeDTO getEmployeeById(Long employeeId) {
		 Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employeee", "ID", employeeId));
		 return this.modelMapper.map(employee, EmployeeDTO.class);
	}
	@Override
	public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
		Employee employee = this.modelMapper.map(employeeDTO, Employee.class);
		employee.setAvailableLeaves(employee.getTotalLeaves());
		return this.modelMapper.map(this.employeeRepository.save(employee), EmployeeDTO.class);
	}
	@Override
	public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
		
		 Employee employee = this.employeeRepository.findById(employeeDTO.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee", "ID", employeeDTO.getEmployeeId()));
		 employee = this.modelMapper.map(employeeDTO, Employee.class);
		 return this.modelMapper.map(this.employeeRepository.save(employee), EmployeeDTO.class);
	}
	@Override
	public boolean upload(MultipartFile file) {
		this.fileUpload.upload(file);
		return false;
	}
	@Override
	public EmployeeDTO getEmployeeByEmail(String employeeEmail) {
		
		 Employee employee = this.employeeRepository.findByEmployeeEmail(employeeEmail).orElseThrow(() -> new ResourceNotFoundException("Employee", "Employee Email : "+employeeEmail, 404l));
		 System.out.println(employee.getDateOfJoining());
		 return this.modelMapper.map(employee, EmployeeDTO.class);
		
	}
	

}
