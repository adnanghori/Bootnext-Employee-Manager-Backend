package com.bootnext.employee.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootnext.employee.manager.model.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

}
