package com.ps20652.Hotel.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps20652.Hotel.dao.RoleDAO;
import com.ps20652.Hotel.entity.Role;
import com.ps20652.Hotel.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService  {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public Role findbyId(Long id) {
       return roleDAO.findById(id).get();
    }
    
}
