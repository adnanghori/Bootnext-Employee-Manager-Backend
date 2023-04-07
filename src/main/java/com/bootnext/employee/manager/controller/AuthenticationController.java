package com.bootnext.employee.manager.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootnext.employee.manager.configuration.security.JwtUtil;
import com.bootnext.employee.manager.model.User;
import com.bootnext.employee.manager.service.UserService;
import com.bootnext.employee.manager.service.adapter.UserDetailsServiceAdapter;
import com.bootnext.employee.manager.utility.API_Response;
import com.bootnext.employee.manager.utility.JwtRequest;
import com.bootnext.employee.manager.utility.JwtResponse;

import lombok.AllArgsConstructor;

@RestController @AllArgsConstructor 
public class AuthenticationController {
	
	private AuthenticationManager authenticationManager;
	private UserDetailsServiceAdapter userDetailsServiceAdapter;
	private JwtUtil jwtUtil;
	private UserService userService;
	private void authenticate(String username , String password ) {
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException e) {
			
			throw new DisabledException("User Disabled" + e.getMessage());
		}
		catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Credentials " + e.getMessage());
		}
	}
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest){
		try {
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			UserDetails userDetails = this.userDetailsServiceAdapter.loadUserByUsername(jwtRequest.getUsername());
			String token = this.jwtUtil.generateToken(userDetails);
			return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
			
		} catch (UsernameNotFoundException e) {
			// TODO: handle exception
			return new ResponseEntity<API_Response>(API_Response.builder().message("User Not Found").status(false).build(),HttpStatus.NOT_FOUND);
		}
		catch (BadCredentialsException e) {
			// TODO: handle exception
			return new ResponseEntity<API_Response>(API_Response.builder().message("Invalid Credentails").status(false).build(),HttpStatus.UNAUTHORIZED);
		}
	}
	@GetMapping("/current-user")
	 public User getCurrentUser(Principal principal) {
		 System.out.println(principal.getName());
		 User user = this.userService.getUser(principal.getName());
		 System.out.println(user.getAuthorities());
		 return this.userService.getUser(principal.getName());
		
	 }
}
