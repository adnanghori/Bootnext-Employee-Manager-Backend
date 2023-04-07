package com.bootnext.employee.manager.service.adapter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bootnext.employee.manager.model.User;
import com.bootnext.employee.manager.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class UserDetailsServiceAdapter implements UserDetailsService{

	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		 User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found with : "+email));
		 System.out.println(user);
		 return user;
	}

}
