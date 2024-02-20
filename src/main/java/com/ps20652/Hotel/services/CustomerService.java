package com.ps20652.Hotel.services;

import java.util.List;

import com.ps20652.Hotel.DTO.CustomerDTO;
import com.ps20652.Hotel.entity.Customer;



public interface CustomerService {

	public List<Customer> findAll();

	public Customer create(Customer customer);
	
	public Customer findbyId(Long id);
	
	Customer update(Customer customer);


	 public Customer createCustomerFromDTO(CustomerDTO customerDTO);
    

}
