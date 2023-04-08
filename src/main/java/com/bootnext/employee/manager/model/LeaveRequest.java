package com.bootnext.employee.manager.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor  @Entity
public class LeaveRequest{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long requestID;
	private String reason;
	private int numberOfDays;
	private String status;
	@ManyToOne @JsonIgnore
	private Employee employee;
	@Temporal(TemporalType.DATE)
	@CreatedDate
	private Date createdDate;
}