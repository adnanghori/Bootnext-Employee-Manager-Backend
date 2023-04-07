package com.bootnext.employee.manager.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder @AllArgsConstructor
public class API_Response {

	private String message;
	private boolean status;
}
