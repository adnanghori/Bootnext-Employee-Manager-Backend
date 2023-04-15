package com.bootnext.employee.manager.service.adapter;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bootnext.employee.manager.exception.UserExistException;
import com.bootnext.employee.manager.model.Role;
import com.bootnext.employee.manager.model.User;
import com.bootnext.employee.manager.payload.UserDTO;
import com.bootnext.employee.manager.repository.RoleRepository;
import com.bootnext.employee.manager.repository.UserRepository;
import com.bootnext.employee.manager.service.UserService;
import com.bootnext.employee.manager.utility.AppConstants;
import com.bootnext.employee.manager.utility.FileUpload;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceAdapter implements UserService {
	
	private ModelMapper modelMapper;
	private RoleRepository roleRepository;
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private FileUpload fileUpload;
	@Override
	public UserDTO createAdminUser(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);
		user.setRawPassword(userDTO.getPassword());
//		user.setPassword(bcrypt);
		user.setUserPassword(this.bCryptPasswordEncoder.encode(userDTO.getPassword()));
		Role role = this.roleRepository.findById(AppConstants.ROLE_HR).get();
		user.getRole().add(role);
		return this.modelMapper.map(this.userRepository.save(user), UserDTO.class);
	}

	@Override
	public UserDTO createEmployeeUser(@Valid UserDTO userDTO) {
		  boolean check = this.userRepository.findByEmail(userDTO.getEmail()).isPresent();
		  if(check) {
			  throw new UserExistException("User Already Exist");
		  }
		
		User user = this.modelMapper.map(userDTO, User.class);
		user.setRawPassword(userDTO.getPassword());
		user.setUserPassword(this.bCryptPasswordEncoder.encode(userDTO.getPassword()));
		Role role = this.roleRepository.findById(AppConstants.ROLE_EMPLOYEE).get();
		user.getRole().add(role);
		user.setImageName("default.png");
		return this.modelMapper.map(this.userRepository.save(user), UserDTO.class);
	}

	@Override
	public User getUser(String name) {
		User user = this.userRepository.findByEmail(name).get();
		return user;
	}

	@Override
	public boolean upload(MultipartFile file) {
		
		return false;
	}

	@Override
	public UserDTO createManager(@Valid UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);
		user.setRawPassword(userDTO.getPassword());
		user.setUserPassword(this.bCryptPasswordEncoder.encode(userDTO.getPassword()));
		Role role = this.roleRepository.findById(AppConstants.ROLE_MANAGER).get();
		user.getRole().add(role);
		return this.modelMapper.map(this.userRepository.save(user), UserDTO.class);
	}

}
