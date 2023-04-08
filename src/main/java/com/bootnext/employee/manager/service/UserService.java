package com.bootnext.employee.manager.service;

import org.springframework.web.multipart.MultipartFile;

import com.bootnext.employee.manager.model.User;
import com.bootnext.employee.manager.payload.UserDTO;

import jakarta.validation.Valid;


public interface UserService {


	public UserDTO createAdminUser(UserDTO userDTO);

	public UserDTO createEmployeeUser( UserDTO userDTO);

	public User getUser(String name);
	public boolean upload(MultipartFile file);

	public UserDTO createManager(@Valid UserDTO userDTO);
}
