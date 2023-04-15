package com.bootnext.employee.manager.service;

import java.util.List;

import com.bootnext.employee.manager.model.LeaveRequest;
import com.bootnext.employee.manager.payload.LeaveRequestDTO;

public interface LeaveRequestService {

	LeaveRequestDTO applyLeave(LeaveRequestDTO leaveRequestDTO,String email);

	List<LeaveRequestDTO> getEmployeeLeaveRequests(String email);

	List<LeaveRequest> getAllLeaveRequests();

	boolean approveLeave(Long requestID);

	boolean rejectLeaveRequest(Long requestID);
	
}
