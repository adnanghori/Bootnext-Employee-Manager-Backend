package com.bootnext.employee.manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Role {
	@Id
	private Integer roleID;
	private String role;
}
