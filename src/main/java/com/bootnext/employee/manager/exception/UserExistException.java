package com.bootnext.employee.manager.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserExistException extends RuntimeException {

	public UserExistException(String message){
		super(message);
	}
}
