package com.bootnext.employee.manager.service.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	private JavaMailSender javaMailSender;
	private Environment environment;
	@Override
	public LeaveRequestDTO applyLeave(LeaveRequestDTO leaveRequestDTO,String email) {
		LeaveRequest leaveRequest = this.modelMapper.map(leaveRequestDTO, LeaveRequest.class);
		Employee employee = this.employeeRepository.findByEmployeeEmail(email).get();
		leaveRequest.setEmployee(employee);
		leaveRequest.setStatus("Pending");
		employee.setAvailableLeaves(employee.getAvailableLeaves()-leaveRequest.getNumberOfDays());
		if(leaveRequest.getNumberOfDays()>employee.getAvailableLeaves()) {
			throw new InsufficientBalanceException("Insufficient Balance");
		}
		else {
			
			LeaveRequest save = this.leaveRequestRepository.save(leaveRequest);
			this.employeeRepository.save(employee);
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(this.environment.getProperty("spring.mail.username")); // company mail address
			simpleMailMessage.setTo("adnanghori@zohomail.in");// manager mail address
			simpleMailMessage.setText(leaveRequest.getReason());
			simpleMailMessage.setSubject(employee.getEmployeeName()+" Has Requested Leave For " + leaveRequest.getNumberOfDays() );
			 
			this.javaMailSender.send(simpleMailMessage);
			return this.modelMapper.map(save, LeaveRequestDTO.class);
		}

	}

	@Override
	public List<LeaveRequestDTO> getEmployeeLeaveRequests(String email) {
		Long employeeID = this.employeeRepository.findByEmployeeEmail(email).get().getEmployeeID();
		List<LeaveRequest> leaveRequests = this.leaveRequestRepository.getLeaveRequestByEmployeeId(employeeID);
		return leaveRequests.stream().map(leaveRequestDTO -> this.modelMapper.map(leaveRequestDTO, LeaveRequestDTO.class) ).collect(Collectors.toList());

	}

	@Override
	public List<LeaveRequest> getAllLeaveRequests() {
		
		return this.leaveRequestRepository.getAllLeaveRequests();		
	}

	@Override
	public boolean approveLeave(Long requestID) {
		 LeaveRequest leaveRequest = this.leaveRequestRepository.findById(requestID).get();
		 leaveRequest.setStatus("Approved");
		 this.leaveRequestRepository.save(leaveRequest);
		 
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom("adnanghori12@gmail.com");
			simpleMailMessage.setTo(leaveRequest.getEmployee().getEmployeeEmail());
			simpleMailMessage.setText("Dear "+ leaveRequest.getEmployee().getEmployeeName() + "Your Leave Request For Days " + leaveRequest.getNumberOfDays() + "Has Been Approved");
			simpleMailMessage.setSubject("Leave Request Approved");
			javaMailSender.send(simpleMailMessage);
		return true;
	}

	@Override
	public boolean rejectLeaveRequest(Long requestID) {
		LeaveRequest leaveRequest = this.leaveRequestRepository.findById(requestID).get();
		 leaveRequest.setStatus("Rejected");
		 this.leaveRequestRepository.save(leaveRequest);
		 
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(this.environment.getProperty("spring.mail.username"));
			simpleMailMessage.setTo(leaveRequest.getEmployee().getEmployeeEmail());
			simpleMailMessage.setText("Dear "+ leaveRequest.getEmployee().getEmployeeName() + "Your Leave Request For Days " + leaveRequest.getNumberOfDays() + "Has Been Rejected");
			simpleMailMessage.setSubject("Leave Request Rejected");
			this.javaMailSender.send(simpleMailMessage);
		return true;
	}


}
