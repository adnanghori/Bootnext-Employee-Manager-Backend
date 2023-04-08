package com.bootnext.employee.manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootnext.employee.manager.payload.UserDTO;
import com.bootnext.employee.manager.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController 
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> createAdminUser(@Valid @RequestBody UserDTO userDTO){
		UserDTO createdUser = this.userService.createAdminUser(userDTO);
		return new ResponseEntity<UserDTO>(createdUser,HttpStatus.CREATED);
	}
	@PostMapping("/create-employee")
	public ResponseEntity<UserDTO> createEmployeeUser(@Valid @RequestBody UserDTO userDTO){
		UserDTO employeeUser = this.userService.createEmployeeUser(userDTO);
		return new ResponseEntity<UserDTO>(employeeUser,HttpStatus.CREATED);
	}
	@PostMapping("/create-manager")
	public ResponseEntity<UserDTO> createManager(@Valid @RequestBody UserDTO userDTO){
		UserDTO manager =  this.userService.createManager(userDTO);
		return new ResponseEntity<UserDTO>(manager,HttpStatus.CREATED);
	}

}
