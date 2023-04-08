package com.bootnext.employee.manager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootnext.employee.manager.model.LeaveRequest;
import com.bootnext.employee.manager.payload.LeaveRequestDTO;
import com.bootnext.employee.manager.service.LeaveRequestService;
import com.bootnext.employee.manager.utility.API_Response;

import lombok.AllArgsConstructor;

@RestController @AllArgsConstructor
@RequestMapping("/api/leave-request")
public class LeaveRequestController {

	private LeaveRequestService leaveRequestService;
	
	@PostMapping("/")
	public ResponseEntity<?> applyLeave(@RequestBody LeaveRequestDTO leaveRequestDTO, Principal principal){
		
		this.leaveRequestService.applyLeave(leaveRequestDTO,principal.getName());
		return new ResponseEntity<API_Response>(API_Response.builder().message("Leave Request Raised").status(true).build(),HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<?> getEmployeeLeaveRequests(Principal principal){
		List<LeaveRequestDTO> leaveRequestDTOs = this.leaveRequestService.getEmployeeLeaveRequests(principal.getName());
		return new ResponseEntity<List<LeaveRequestDTO>>(leaveRequestDTOs,HttpStatus.OK);
		
	}
	@GetMapping("/all-leave-requests")
	public ResponseEntity<List<LeaveRequest>> getAllLeaveRequest(){
		List<LeaveRequest> allLeaveRequests = this.leaveRequestService.getAllLeaveRequests();
		return new ResponseEntity<List<LeaveRequest>>(allLeaveRequests,HttpStatus.OK);
	}
	
	@PutMapping("/{requestID}")
	public ResponseEntity<API_Response> approveLeaveRequest(@PathVariable Long requestID){
		this.leaveRequestService.approveLeave(requestID);
		 return new ResponseEntity<API_Response>(API_Response.builder().message("Approved").status(true).build(),HttpStatus.OK);
	}
}
