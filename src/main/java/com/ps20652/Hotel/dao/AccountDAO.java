package com.ps20652.Hotel.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ps20652.Hotel.entity.Account;

public interface AccountDAO extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);
    Account findByUsername(String username);
    Long findAccountIdByUsername(String username);
     Account findByAccountId(Long accountId);
    
}
