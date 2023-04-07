package com.bootnext.employee.manager.payload;

import java.util.HashSet;
import java.util.Set;

import com.bootnext.employee.manager.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDTO {

	private Long userID;
	@Size(min = 4,message = "Minimun 4 char")
	private String username;
	@Size(min = 4,message = "Min Pass 4 char")
	private String password;
	@NotEmpty @Email(message = "Email not valid")
	private String email;
	private Set<Role> role = new HashSet<>();
}
