package com.bootnext.employee.manager.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data @Entity
public class LeaveRequest{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long requestID;
	private String reason;
	private int numberOfDays;
	private String status;
	@ManyToOne
	private Employee employee;
	@Temporal(TemporalType.DATE)
	@CreatedDate
	private Date createdDate;
}