package com.ps20652.Hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps20652.Hotel.entity.Role;

public interface RoleDAO extends JpaRepository<Role, Long> {
    
}