package com.bootnext.employee.manager.service;

import com.bootnext.employee.manager.model.User;
import com.bootnext.employee.manager.payload.UserDTO;


public interface UserService {


	public UserDTO createAdminUser(UserDTO userDTO);

	public UserDTO createEmployeeUser( UserDTO userDTO);

	public User getUser(String name);
}
