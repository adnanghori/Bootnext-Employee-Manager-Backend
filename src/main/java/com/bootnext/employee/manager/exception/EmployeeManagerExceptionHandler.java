package com.bootnext.employee.manager.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bootnext.employee.manager.utility.API_Response;



@RestControllerAdvice
public class EmployeeManagerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<API_Response> resourceNotFoundExceptionHandler(ResourceNotFoundException rnfe){
			String message = rnfe.getMessage();
			API_Response api_Response = new API_Response(message, false);
			return new ResponseEntity<API_Response>(api_Response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> argumentNotValidException(MethodArgumentNotValidException manve){
		
		Map<String, String > response  = new HashMap<>();
		manve.getBindingResult().getAllErrors().forEach((error->{
			String fieldName = ((FieldError)error).getField();
			String defaultMessage = error.getDefaultMessage();
			response.put(fieldName, defaultMessage);
		}));
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<API_Response> accessDenied(){
		return new ResponseEntity<API_Response>(new API_Response("UnAuthorized Request",false),HttpStatus.FORBIDDEN);
	}
	@ExceptionHandler(InsufficientBalanceException.class) 
	public ResponseEntity<API_Response> insufficientBalance(){
		return new ResponseEntity<API_Response>(API_Response.builder().message("Insufficient Days Left In Your Account").status(false).build(),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(UserExistException.class)
	public ResponseEntity<API_Response> userExistException(){
		return new ResponseEntity<API_Response>(API_Response.builder().message("User Already Exist").status(false).build(),HttpStatus.BAD_REQUEST);
	}
	
}
