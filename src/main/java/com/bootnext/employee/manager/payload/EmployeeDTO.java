package com.bootnext.employee.manager.payload;



import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class EmployeeDTO {

	private Long employeeId;
	@NotEmpty
	@Size(min = 4,message = "min length of username must be 4 char ")
	private String employeeName;
	@Email(message = "Email address not valid")
	private String employeeEmail;
	private int totalLeaves;
	private int availableLeaves;
	private String designation;
	private Date dateOfJoining = new Date();
}

