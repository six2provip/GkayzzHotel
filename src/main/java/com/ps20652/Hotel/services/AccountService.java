package com.ps20652.Hotel.services;

import java.util.List;

import com.ps20652.Hotel.entity.Account;




public interface AccountService {

	public List<Account> findAll();

	public Account create(Account account);
	
	public Account findbyId(Long id);
	
	Account update(Account account);

	public boolean checkLogin(Account account);

	public Long findAccountIdByUsername(String username);
}
