package com.bootnext.employee.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import com.bootnext.employee.manager.model.LeaveRequest;

import jakarta.transaction.Transactional;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

	@Modifying 
	@Transactional
	@Query(value = "Select * FROM leave_request WHERE employee_employeeid = ?1",nativeQuery = true)
	public List<LeaveRequest> getLeaveRequestByEmployeeId(Long employeeeId);
}
