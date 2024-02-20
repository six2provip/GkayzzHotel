package com.ps20652.Hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps20652.Hotel.entity.Customer;

public interface CustomerDAO extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}
