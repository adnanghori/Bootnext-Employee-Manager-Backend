package com.bootnext.employee.manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bootnext.employee.manager.payload.EmployeeDTO;
import com.bootnext.employee.manager.service.EmployeeService;
import com.bootnext.employee.manager.utility.API_Response;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {

	private EmployeeService employeeService;
	
	@GetMapping("/")
	public ResponseEntity<List<EmployeeDTO>> getEmployees(){
		List<EmployeeDTO> allEmployees = this.employeeService.getAllEmployees();
		return new ResponseEntity<List<EmployeeDTO>>(allEmployees,HttpStatus.FOUND);
	}
	@GetMapping("/{employeeId}")
	public ResponseEntity<EmployeeDTO> getEmployeeByID(@PathVariable Long employeeId){
		EmployeeDTO employee = this.employeeService.getEmployeeById(employeeId);
		return new ResponseEntity<EmployeeDTO>(employee,HttpStatus.FOUND);
	}
	@GetMapping("/by-email/{employeeEmail}")
	public ResponseEntity<EmployeeDTO> getEmployeeByEmail(@PathVariable String employeeEmail){
		EmployeeDTO employee = this.employeeService.getEmployeeByEmail(employeeEmail);
		return new ResponseEntity<EmployeeDTO>(employee,HttpStatus.OK);
	}
	@PostMapping("/")
	public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO )
	{
		EmployeeDTO addEmployee = this.employeeService.addEmployee(employeeDTO);
		return new ResponseEntity<EmployeeDTO>(addEmployee,HttpStatus.CREATED);
	}
	@PutMapping("/")
	public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO){
		EmployeeDTO updateEmployee = this.employeeService.updateEmployee(employeeDTO);
		return new ResponseEntity<EmployeeDTO>(updateEmployee,HttpStatus.OK);
	}
	@PostMapping("/{employeeId}/upload-file")
	public ResponseEntity<API_Response> upload(@PathVariable Long employeeId , @RequestParam("file") MultipartFile file ){
		
		if(file.isEmpty()) {
			return new ResponseEntity<API_Response>(API_Response.builder().message("file can't be empty").status(false).build(),HttpStatus.BAD_REQUEST);	
		}
		boolean upload = this.employeeService.upload(file);
		if(upload) {
			return new ResponseEntity<API_Response>(new API_Response(ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString(),upload),HttpStatus.OK);
		}
		else
		return new ResponseEntity<API_Response>(API_Response.builder().message("Something Went Wrong").status(false).build(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
