package com.ps20652.Hotel.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps20652.Hotel.DTO.CustomerDTO;
import com.ps20652.Hotel.dao.CustomerDAO;
import com.ps20652.Hotel.entity.Customer;
import com.ps20652.Hotel.services.CustomerService;
import com.ps20652.Hotel.services.EmailService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	@Override
	public List<Customer> findAll() {

		return customerDAO.findAll();
	}

	@Override
	public Customer create(Customer customer) {

		if (customerDAO.existsByEmail(customer.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		return customerDAO.save(customer);
	}

	@Override
	public Customer findbyId(Long id) {

		return customerDAO.findById(id).get();
	}

	@Override
	public Customer update(Customer customer) {
		return customerDAO.save(customer);
	}

	@Override
	public Customer createCustomerFromDTO(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setEmail(customerDTO.getEmail());
		customer.setPhone(customerDTO.getPhone());

		// Các thông tin khác có thể cần sao chép từ CustomerDTO vào Customer

		return customer;
	}

}
