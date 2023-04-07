package com.bootnext.employee.manager.service.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bootnext.employee.manager.exception.InsufficientBalanceException;
import com.bootnext.employee.manager.model.Employee;
import com.bootnext.employee.manager.model.LeaveRequest;
import com.bootnext.employee.manager.payload.LeaveRequestDTO;
import com.bootnext.employee.manager.repository.EmployeeRepository;
import com.bootnext.employee.manager.repository.LeaveRequestRepository;
import com.bootnext.employee.manager.service.LeaveRequestService;

import lombok.AllArgsConstructor;
@Service @AllArgsConstructor 
public class LeaveRequestServiceAdapter implements LeaveRequestService {

	private LeaveRequestRepository leaveRequestRepository;
	private EmployeeRepository employeeRepository;
	private ModelMapper modelMapper;

	@Override
	public LeaveRequestDTO applyLeave(LeaveRequestDTO leaveRequestDTO,String email) {
		LeaveRequest leaveRequest = this.modelMapper.map(leaveRequestDTO, LeaveRequest.class);
		Employee employee = this.employeeRepository.findByEmployeeEmail(email).get();
		leaveRequest.setEmployee(employee);
		leaveRequest.setStatus("Pending");
		if(leaveRequest.getNumberOfDays()>employee.getAvailableLeaves()) {
			throw new InsufficientBalanceException("Insufficient Balance");
		}
		else {
			LeaveRequest save = this.leaveRequestRepository.save(leaveRequest);
			return this.modelMapper.map(save, LeaveRequestDTO.class);
		}

	}

	@Override
	public List<LeaveRequestDTO> getEmployeeLeaveRequests(String email) {
		Long employeeID = this.employeeRepository.findByEmployeeEmail(email).get().getEmployeeID();
		List<LeaveRequest> leaveRequests = this.leaveRequestRepository.getLeaveRequestByEmployeeId(employeeID);
		return leaveRequests.stream().map(leaveRequestDTO -> this.modelMapper.map(leaveRequestDTO, LeaveRequestDTO.class) ).collect(Collectors.toList());

	}

}
