package com.bootnext.employee.manager.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InsufficientBalanceException extends RuntimeException {

	public InsufficientBalanceException(String message) {
		super(message);
	}
}
