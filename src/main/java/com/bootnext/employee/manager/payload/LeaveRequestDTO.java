package com.bootnext.employee.manager.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LeaveRequestDTO {

	private String reason;
	private int numberOfDays;
	private Date createdDate = new Date();
	private String status;
}
